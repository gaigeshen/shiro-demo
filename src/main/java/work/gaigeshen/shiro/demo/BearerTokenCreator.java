package work.gaigeshen.shiro.demo;

/**
 * 用于访问令牌创建或者校验
 *
 * @author gaigeshen
 */
public interface BearerTokenCreator {

  /**
   * 创建访问令牌
   *
   * @param userProfile 用户信息
   * @return 访问令牌
   */
  String createToken(UserProfile userProfile);

  /**
   * 校验访问令牌并且转换为用户信息返回
   *
   * @param token 访问令牌
   * @return 用户信息
   */
  UserProfile parseToken(String token);

}
