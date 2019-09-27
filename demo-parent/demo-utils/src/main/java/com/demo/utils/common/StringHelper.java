package com.demo.utils.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zzj
 * @create 2018-11-29 17:03
 **/
public class StringHelper {

    /**
     * 小数转百分比 0.2 --20%
     *
     * @param number
     * @return
     */
    public static String numberToPercent(double number) {
        NumberFormat num = NumberFormat.getPercentInstance();
        num.setMaximumIntegerDigits(3);
        num.setMaximumFractionDigits(0);
        return num.format(number);
    }

    /**
     * 手机验证
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        //该正则支持港澳台
        String regex = "^[1][3-8]\\d{9}$|^([6|9])\\d{7}$|^[0][9]\\d{8}$|^[6]([8|6])\\d{5}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证是否为email
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return Pattern.matches(regex, email);
    }
    /**
     * 过滤特殊字符表情符号
     */
    private static String pattern;
    private static Pattern emoji;
    static {
        pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        emoji=Pattern.compile(pattern);
    }
    public static String filter(String str) {

        if(str.trim().isEmpty()){
            return str;
        }
        return emoji.matcher(str).replaceAll("");
    }

    /**
     * 去除最后一个指定分隔符
     *
     * @param str
     * @param separator
     * @return
     */
    public static String removeLastSeparator(String str, String separator) {
        int indx = str.lastIndexOf(separator);
        if (indx != -1) {
            str = str.substring(0, indx) + str.substring(indx + 1, str.length());
        }
        return str;
    }


    /**
     * 按子节长度截取字符串(支持截取带HTML代码样式的字符串)<br>
     * 如：<span>中国人发在线</span> 当截取2个字节得到的结果是：<span>中国
     *
     * @param param
     *            将要截取的含html代码的字符串参数
     * @param length
     *            截取的字节长度
     * @return 返回截取后的字符串
     * @author YangJunping
     * @date 2010-7-15
     */
    public static String subStringHTML(String param, int length) {
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        // 是不是HTML代码
        boolean isCode = false;
        // 是不是HTML特殊字符,如
        boolean isHTML = false;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            if (!isCode && !isHTML) {
                n = n + 1;
                // UNICODE码字符占两个字节
                if ((temp + "").getBytes().length > 1) {
                    n = n + 1;
                }
            }
            result.append(temp);
            if (n >= length) {
                result.append("...");
                break;
            }
        }
        return fix(result.toString());
    }

    /**
     * 补全HTML代码<br>
     * 如：<span>中国 ---> <span>中国</span>
     *
     * @param str
     * @return
     * @author YangJunping
     * @date 2010-7-15
     */
    public static String fix(String str) {
        // 存放修复后的字符串
        StringBuffer fixed = new StringBuffer();
        TagsList[] unclosedTags = getUnclosedTags(str);
        // 生成新字符串
        for (int i = unclosedTags[0].size() - 1; i > -1; i--) {
            fixed.append("<" + unclosedTags[0].get(i) + ">");
        }
        fixed.append(str);
        for (int i = unclosedTags[1].size() - 1; i > -1; i--) {
            String s = null;
            if ((s = unclosedTags[1].get(i)) != null) {
                fixed.append("</" + s + ">");
            }
        }
        return fixed.toString();
    }

    public static TagsList[] getUnclosedTags(String str) {
        // 存放标签
        StringBuffer temp = new StringBuffer();
        TagsList[] unclosedTags = new TagsList[2];
        // 前不闭合，如有</div>而前面没有<div>
        unclosedTags[0] = new TagsList();
        // 后不闭合，如有<div>而后面没有</div>
        unclosedTags[1] = new TagsList();
        // 记录双引号"或单引号'
        boolean flag = false;
        // 记录需要跳过''还是""
        char currentJump = ' ';
        // 当前 & 上一个
        char current = ' ', last = ' ';
        // 开始判断
        for (int i = 0; i < str.length();) {
            // 读取一个字符
            current = str.charAt(i++);
            if (current == '"' || current == '\'') {
                // 若为引号，flag翻转
                flag = flag ? false : true;
                currentJump = current;
            }
            if (!flag) {
                if (current == '<') {
                    // 开始提取标签
                    current = str.charAt(i++);
                    if (current == '/') {
                        // 标签的闭合部分，如</div>
                        current = str.charAt(i++);
                        // 读取标签
                        while (i < str.length() && current != '>') {
                            temp.append(current);
                            current = str.charAt(i++);
                        }
                        // 从tags_bottom移除一个闭合的标签
                        if (!unclosedTags[1].remove(temp.toString())) {
                            // 若移除失败，说明前面没有需要闭合的标签
                            unclosedTags[0].add(temp.toString());
                            // 此标签需要前闭合
                        }
                        // 清空temp
                        temp.delete(0, temp.length());

                    } else {
                        // 标签的前部分，如<div>
                        last = current;
                        while (i < str.length() && current != ' ' && current != ' ' && current != '>') {
                            temp.append(current);
                            last = current;
                            current = str.charAt(i++);
                        }
                        // 已经读取到标签，跳过其他内容，如<div id=test>跳过id=test
                        while (i < str.length() && current != '>') {
                            last = current;
                            current = str.charAt(i++);
                            if (current == '"' || current == '\'') {
                                // 判断引号
                                flag = flag ? false : true;
                                currentJump = current;
                                if (flag) {
                                    // 若引号不闭合，跳过到下一个引号之间的内容
                                    while (i < str.length() && str.charAt(i++) != currentJump) {;}
                                    current = str.charAt(i++);
                                    flag = false;
                                }
                            }
                        }
                        if (last != '/' && current == '>') {
                            // 判断这种类型：<TagName />
                            unclosedTags[1].add(temp.toString());
                        }
                        temp.delete(0, temp.length());
                    }
                }
            } else {
                while (i < str.length() && str.charAt(i++) != currentJump){; }
                // 跳过引号之间的部分
                flag = false;
            }
        }
        return unclosedTags;
    }




    public static void main(String[] args) {
       String message= "{\"categoryName\":\"类别名称不多于6个字符 , 类别名称只允许输入中英文和数字 , 类别名称不能为空\",\"categoryName2\":\"类别名称不能为空\"}";
        System.out.println(JsonUtils.getMessage(message));
    }
}
