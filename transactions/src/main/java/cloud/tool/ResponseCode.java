/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.tool;

public enum ResponseCode {
    CODE_SUCCESS(0, "200"), // 成功
    CODE_NOT_EXISTS(1, "不存在"), // 不存在
    CODE_DUPLICATED_KEY(2, "主键重复"), // 主键重复
    CODE_SERVER_ERROR(3, "系统错误"), // 系统错误
    CODE_VALIDATE_EXPIRED(4, "验证码已过期"), // 验证码已过期
    CODE_PASS_LIMIT(5, "验证码发送超过限制"), // 验证码发送超过限制
    CODE_INPUT_NULL(6, "输入参数为空"), // 输入参数为空
    CODE_DATA_CORUPT(7, "数据损坏"), // 数据损坏
    CODE_NOT_AUTHORIZED(8, "未授权访问"), // 未授权访问
    PASSWORD_NOT_CORRECT(9, "密码不正确"), // 密码不正确
    CARD_VALIDATE_ERROR(10, "获取验证码错误"), // 获取验证码错误
    CODE_VALIDATE_NOT_EXISTS(11, "验证码不存在"), // 验证码不存在
    CODE_UPLOAD_FAILURE_OUTOFMAXSIZE(12, "超过最大上传限制"), // 超过最大上传限制
    CODE_UPLOAD_NOT_SUPPORT_CONTENTTYPE(13, "不支持上传文件的类型"), // 不支持上传文件的类型
    CODE_USERID_NOT_PASS(14, "没有传入登录账号"), // 没有传入登录账号
    CODE_USERID_NOT_ONLY(15, "用户已存在或者不唯一"), // 用户不存在或者不唯一
    CODE_PARAMETER_ERROR(16, "参数错误"), // 参数错误
    CODE_FORCE_OFF_LINE(17, "强制下线"), // 强制下线
    CODE_LOGIN_NEED_VALIDATE(18, "登录需要验证码"), // 登录需要验证码
    CODE_REGISTER_NOT_PASS(19, "机构用户已经注册但未审核通过"), // 机构用户已经注册但未审核通过
    CODE_PRICE_INVERSE(20, "价格倒挂"), // 价格倒挂
    CODE_BOND_CAL_NOT_SUPPORT(21, "债券无法计算"), // 债券无法计算
    CODE_BOND_CAL_NET_PRICE_ILLEGAL(22, "净价输入非法"), // 净价输入非法
    CODE_BOND_CAL_FULL_PRICE_ILLEGAL(23, "全价输入非法"); // 全价输入非法
/*	CODE_BILL_STATUS_CHANGED(24, "申购业务状态已变更，请重新获取数据"), CODE_BILL_BUSINESS_MULTIPLE(25, "您不能同时处理多笔业务，请处理后再次点击"), CODE_BILL_CANNOT_UPDATE
(26, "业务状态已变更，请重新获取数据"), CODE_EBABUY_NOT_EXIST(27,
			"客户无归属，不可申报"), CODE_BILL_APPLY_STATUS_CHANGED(28, "申报业务状态已变更，请重新获取数据"), CODE_BILL_BBA_STATUS_CHANGED(29, "业务状态已变更，请重新获取数据"),
			CODE_BARGAINING_QUOTE_STATUS_CHANGED(30,
					"业务状态已变更，请重新获取数据"), CODE_INTERVAL_TOO_SHORT(31, "调用太频繁"), CODE_VALIDATE_IMAGE_NULL(32, "图形验证码为空"),
					CODE_VALIDATE_IMAGE_NOT_EQUAL(33, "图形验证码不正确");*/

    private ResponseCode(Integer value) {
        this.value = value;
    }

    private ResponseCode(Integer value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    private Integer value  = 0;
    private String  reason = "";

    public Integer getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

}
