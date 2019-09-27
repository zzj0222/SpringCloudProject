手机号登陆、第三方登录和安全认证核心服务

修改文件：
文件 security\core\social\qq\config\QQAutoConfig.java
@ConditionalOnProperty(prefix = "imooc.security.social.qq", name = "app-id") 修改为 @ConditionalOnProperty(prefix = "demo.security.social.qq", name = "app-id")

文件 security\core\properties\SecurityProperties.java
@ConfigurationProperties(prefix = "imooc.security") 修改为 @ConfigurationProperties(prefix = "demo.security")

文件 security\core\properties\OAuth2Properties.java
private String jwtSigningKey = "imooc";修改为	private String jwtSigningKey = "lai-ai";

文件 security\core\social\SocialConfig.java
repository.setTablePrefix("imooc_"); 修改为 repository.setTablePrefix("demo_");

文件 security\core\social\weixin\config\WeixinAutoConfiguration.java
@ConditionalOnProperty(prefix = "imooc.security.social.weixin", name = "app-id") 修改为 @ConditionalOnProperty(prefix = "imooc.security.social.weixin", name = "app-id")

文件 security\core\properties\BrowserProperties.java
private String signUpUrl = "/imooc-signUp.html"; 修改为 	private String signUpUrl = "/demo-signUp.html";

文件 security\core\properties\SecurityConstants.java
String DEFAULT_SIGN_IN_PAGE_URL = "/imooc-signIn.html"; 修改为  String DEFAULT_SIGN_IN_PAGE_URL = "/demo-signIn.html";
String DEFAULT_SESSION_INVALID_URL = "/imooc-session-invalid.html"; 修改为 	String DEFAULT_SESSION_INVALID_URL = "/demo-session-invalid.html";


文件 security\core\authentication\mobile\SmsCodeAuthenticationSecurityConfig.java
    @Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
变量重新命名为：

     @Autowired
	private AuthenticationSuccessHandler demoAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler demoAuthenticationFailureHandler;
文件 security\core\authentication\FormAuthenticationConfig.java 
	@Autowired
	protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;
变量重命名为：
	@Autowired
	protected AuthenticationSuccessHandler demoAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler demoAuthenticationFailureHandler;
文件名修改：
security\core\authorize\ImoocAuthorizeConfigManager.java 修改为 security\core\authorize\DemoAuthorizeConfigManager.java

security\core\authorize\ImoocAuthorizeConfigProvider.java 修改为 security\core\authorize\DemoAuthorizeConfigProvider.java

security\core\social\support\ImoocSpringSocialConfigurer.java 修改为 security\core\social\support\DemoSpringSocialConfigurer.java 

security\core\social\view\ImoocConnectionStatusView.java 修改为  security\core\social\view\DemoConnectionStatusView.java


security\core\social\view\ImoocConnectView.java 修改为  security\core\social\view\DemoConnectView.java


修改方法名：
文件 security\core\social\SocialConfig.java
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig()
	修改为
	@Bean
	public SpringSocialConfigurer demoSocialSecurityConfig()



