package com.example.careme.chatsample.features.demo.custom.media.holders;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessageHolders;
import com.example.careme.R;
import com.example.careme.chatsample.common.data.model.Message;
import com.example.careme.chatsample.utils.FormatUtils;
import com.stfalcon.chatkit.utils.DateFormatter;

/*
 * Created by troy379 on 05.04.17.
 */
public class IncomingVoiceMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<Message> {

    private TextView tvDuration;
    private TextView tvTime;

    public IncomingVoiceMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        tvDuration = itemView.findViewById(R.id.duration);
        tvTime = itemView.findViewById(R.id.time);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        tvDuration.setText(
                FormatUtils.getDurationString(
                        message.getVoice().getDuration()));
        tvTime.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));
    }
}
