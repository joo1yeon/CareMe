package com.example.careme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.careme.chatsample.common.data.fixtures.MessagesFixtures;
import com.example.careme.chatsample.common.data.model.Message;
import com.example.careme.chatsample.features.demo.DemoMessagesActivity;
import com.example.careme.chatsample.features.demo.def.DefaultMessagesActivity;
import com.example.careme.chatsample.features.demo.styled.StyledMessagesActivity;
import com.example.careme.chatsample.utils.AppUtils;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

import static android.media.audiofx.AudioEffect.CONTENT_TYPE_VOICE;

public class ChatbotActivity extends Fragment
        implements
        MessagesListAdapter.SelectionListener,
        MessagesListAdapter.OnLoadMoreListener,
        MessagesListAdapter.OnMessageLongClickListener<Message>,
        MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageInput.TypingListener,
        MessageHolders.ContentChecker<Message> {

    View view;
    private static final int TOTAL_MESSAGES_COUNT = 100;

    protected final String senderId = "0";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<Message> messagesAdapter;

    private Menu menu;
    private int selectionCount;
    private Date lastLoadedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_default_messages, container, false);

        //imageLoader = ((imageView, url, payload) -> Picasso.get().load(url).into(imageView));
        setHasOptionsMenu(true); // 메세지 클릭 시 옵션

        this.messagesList = view.findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = view.findViewById(R.id.input);
        input.setInputListener(this);
        input.setTypingListener(this);
        input.setAttachmentsListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
    }

    // 메시지 길게 누르면 메뉴 나오게
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                messagesAdapter.deleteSelectedMessages();
                break;
            case R.id.action_copy:
                messagesAdapter.copySelectedMessagesText(getContext(), getMessageStringFormatter(), true);
                AppUtils.showToast(getContext(), R.string.copied_message, true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        if (selectionCount == 0) {
//            super.onBackPressed();
//        } else {
//            messagesAdapter.unselectAllItems();
//        }
//    }


    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        Log.i("TAG", "onLoadMore: " + page + " " + totalItemsCount);
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages();
        }
    }

    // 메시지 선택 개수에 따라 삭제, 복사 메뉴 보여짐
    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
        menu.findItem(R.id.action_copy).setVisible(count > 0);
    }

    protected void loadMessages() {
        // imitation of internet connection
        new Handler().postDelayed(() -> {
            ArrayList<Message> messages = MessagesFixtures.getMessages(lastLoadedDate);
            lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
            messagesAdapter.addToEnd(messages, false);
        }, 1000);
    }

    private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
        return message -> {
            String createdAt = new SimpleDateFormat("MM d, EEE 'at' h:mm a", Locale.getDefault())
                    .format(message.getCreatedAt());

            String text = message.getText();
            if (text == null)
                text = "[attachment]";

            return String.format(Locale.getDefault(), "%s: %s (%s)",
                    message.getUser().getName(), text, createdAt);
        };
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, DefaultMessagesActivity.class));
    }

    private MessagesList messagesList;

    // 전송 버튼 눌렀을 때
    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString(), true), true);
        return true;
    }

    // + 버튼 눌렀을 때
    @Override
    public void onAddAttachments() {
        messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(false), true
        );
    }

    private void initAdapter() {
        messagesAdapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setLoadMoreListener(this);
        messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                (view, message) -> AppUtils.showToast(getContext(),
                        message.getUser().getName() + " avatar click", false));
        this.messagesList.setAdapter(messagesAdapter);
    }

    @Override
    public void onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status));
    }

    @Override
    public void onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status));
    }

    @Override
    public void onMessageLongClick(Message message) {
        AppUtils.showToast(getContext(), R.string.on_log_click_message, false);
    }

    @Override
    public boolean hasContentFor(Message message, byte type) {
        if (type == CONTENT_TYPE_VOICE) {
            return message.getVoice() != null
                    && message.getVoice().getUrl() != null
                    && !message.getVoice().getUrl().isEmpty();
        }
        return false;
    }
}
