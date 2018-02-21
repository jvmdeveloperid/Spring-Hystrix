package id.jvmdeveloper.springhystrix.command;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import rx.Observable;
import rx.Single;

/**
 * @author Eko Kurniawan Khannedy
 */
@Slf4j
public class HelloObservableCommand extends HystrixObservableCommand<String> {

  private String name;

  public HelloObservableCommand(String name) {
    super(
        Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("helloGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("helloObservableKey"))
    );
    this.name = name;
  }

  @Override
  protected Observable<String> construct() {
    return Observable.create(subscriber -> {
      if (StringUtils.isNotBlank(name)) {
        log.info("Execute HelloObservableCommand");
        subscriber.onNext("Hello " + name);
        subscriber.onCompleted();
      } else {
        subscriber.onError(new IllegalArgumentException("Name is blank"));
      }
    });
  }

  @Override
  protected Observable<String> resumeWithFallback() {
    return Single.<String>create(subscriber -> {
      log.info("Execute Fallback HelloObservableCommand");
      subscriber.onSuccess("Hello Guest");
    }).toObservable();
  }
}
