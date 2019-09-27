-- shouyingtai.html 前端支付页面
-- zhifujiguo.html 前端支付结果页面
-- shouyingtai.js-前端支付页面js
-- 判断是否微信中的js

	function isWeiXin() {
			var ua = navigator.userAgent.toLowerCase();
			return(/micromessenger/.test(ua)) ? true : false;
		}
		微信h5支付
		if(!isWeiXin()) {
                    $.cookie('wechatPay', true);
                    var orderNo = $("#orderNoInput").val();
                    $("#Wechatpay_btn").show(); // 微信h5
                    $("#payBtn").hide(); // 公众号
                    $.ajax({
                            type: "POST",
                            url: "http://www.test.com/api/wxPay/h5preorder.action",
                            data: {
                                orderNo: orderNo
                            },
                            success: function(data) {
                                // mweb_url h5支付跳转连接
                                that.returnUrl = data.data.payResponse.returnUrl;
                                that.nopayReturnUrl = data.data.payResponse.nopayReturnUrl;
                                that.wechatPay = data.data.mweb_url;
                                $("#Wechatpay_btn").attr('href', that.wechatPay);
                            },
                            error: function(err) {
                                console.log(err)
                            }
                        });
        }
		
	
        $(document).on("click", '#payBtn', function() {
        /*微信公众号支付*/
        		$.cookie('wechatPay', false);
        微信公众号支付：
        $.ajax({
        				type: "GET",
        				url: payUrl + "/wx/auth.action",
        				datatype: "jsonp",
        				data: {
        					scope: 'snsapi_base',
        					userId: userId,
        					orderNo: orderNo
        				},
        				success: function(data) {
        					isAuch = 1;
        					location.href = data.data;
        				},
        				error: function(err) {
        					console.log(err)
        				}
        			});
        }