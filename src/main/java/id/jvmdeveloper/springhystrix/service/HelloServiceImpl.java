package id.jvmdeveloper.springhystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Eko Kurniawan Khannedy
 */
@Service
public class HelloServiceImpl implements HelloService {

  @HystrixCommand(
      commandKey = "helloServiceKey",
      groupKey = "helloGroup",
      fallbackMethod = "helloFallback"
  )
  public String hello(String name) {
    Assert.isTrue(StringUtils.isNotBlank(name), "Name is blank");
    return "Hello " + name;
  }

  public String helloFallback(String name, Throwable throwable) {
    return "Hello Guest";
  }
}
