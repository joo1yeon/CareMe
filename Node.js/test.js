var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(3000, function () {
    console.log('서버 실행 중...');
})

var connection = mysql.createConnection({
    host: "",
    user: "",
    database: "",
    password: "",
    port:
});

connection.connect(function(err){
    if (err) {
        console.error('Database connection failed: ' + err.stack);
        throw  err;
        return;
    }
    console.log("Database Connected!!!!!");
    return;
});

// 테스트
app.get('/test', function (req, res) {
    console.log(req.body);
    var test = req.body.data;

    var sql = 'INSERT INTO test(content) VALUES(?)';
    var params = [test];

    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다.';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = 'test 성공!';
        }

        res.json({
            'code': resultCode,
            'message': message
        })
    })
})

// 회원 가입
app.post('/user/join', function (req, res) {
    console.log(req.body);

    var userName = req.body.userName;
    var userId = req.body.userId;
    var userPwd = req.body.userPwd;

    var sql = 'INSERT INTO Users(id, pwd, name) VALUES(?,?,?)';
    var params = [userId, userPwd, userName];

    connection.query(sql, params, function (err, result) {
        var resultCode;
        var message;

        if (err) {
            resultCode = 404;
            message = '회원가입 에러가 발생했습니다.';
            console.log(err);
        } else {
            resultCode = 200;
            message = '회원가입에 성공했습니다.';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});

// 로그인
app.post('/user/login', function (req, res) {
    console.log(req.body);
    
    var userId = req.body.userId;
    var userPwd = req.body.userPwd;
    var sql = 'SELECT * FROM Users WHERE id = ? AND pwd = ?';
    var params = [userId, userPwd];

    connection.query(sql, params, function (err, result) {
        var resultCode;
        var message;

        if (err) {
            resultCode = 404;
            message = '에러가 발생했습니다.';
            console.log(err);
        } else {
            if (result.length === 0) {
                resultCode = 204;
                message = '존재하지 않는 계정입니다.';
            } else if (userPwd !== result[0].pwd) {
                resultCode = 204;
                message = '비밀번호가 일치하지 않습니다.';
            } else {
                resultCode = 200;
                message = result[0].name + '님 환영합니다!';
            }
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });

});

//connection.end();


