package com.demo.utils.common;
/**
 * 正则表达式验证类
 * @author zzj
 * @create 2019-03-28 9:42
 *  注解 @NotBlank(message = "不能为空"),@Length(min=1, max=6, message="不多于6个字符"),@Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$", message = "只允许输入中英文和数字")
 **/
public class InputValidator {

    /**
     * 只允许输入中英文和数字
     */
    public  static  final  String CHINESE_AND_ENGLISH_AND_FIGURES_VALIDATOR="^[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+$";
    public  static  final  String CHINESE_AND_ENGLISH_AND_FIGURES_MESSAGE="只允许输入中英文和数字";

    /**
     * 只允许输入数字字母下划线验证
     */
    public  static  final  String NUMBERS_AND_LETTERS_AND_UNDERLINED_VALIDATOR="[0-9a-zA-Z_]";
    public  static  final  String NUMBERS_AND_LETTERS_AND_UNDERLINED_MESSAGE="只允许输入数字，字母和下划线";



}
