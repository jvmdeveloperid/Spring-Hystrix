package id.jvmdeveloper.springhystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Eko Kurniawan Khannedy
 */
@Slf4j
public class HelloCommand extends HystrixCommand<String> {

  private String name;

  public HelloCommand(String name) {
    super(
        Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("helloGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("helloKey"))
    );
    this.name = name;
  }

  @Override
  protected String run() throws Exception {
    Assert.isTrue(StringUtils.isNotBlank(name), "Name is blank");
    log.info("Execute HelloCommand");
    return "Hello " + name;
  }

  @Override
  protected String getFallback() {
    log.info("Execute Fallback HelloCommand");
    return "Hello Guest";
  }
}
