package org.Chr.domain.Enum;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTEXT_NOT_NULL(506,"评论内容不能为空"),
    NICKNAME_NOT_NULL(507,"昵称不能为空"),
    PASSWORD_NOT_NULL(508,"密码不能为空"),
    EMAIL_NOT_NULL(509,"邮箱不能为空"),
    NICKNAME_EXIST(510,"昵称已存在"),

    IMG_FILE_ERROR(511,"请上传正确格式的图片"),
    LOGIN_ERROR(505,"用户名或密码错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}