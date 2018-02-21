package id.jvmdeveloper.springhystrix.controller;

import id.jvmdeveloper.springhystrix.command.HelloCommand;
import id.jvmdeveloper.springhystrix.command.HelloObservableCommand;
import id.jvmdeveloper.springhystrix.service.HelloService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * @author Eko Kurniawan Khannedy
 */
@Api
@RestController
public class HelloController {

  @Autowired
  private HelloService helloService;

  @RequestMapping(
      value = "/hello",
      method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE
  )
  public String hello(@RequestParam(value = "name", required = false) String name) {
    return new HelloCommand(name).execute();
  }

  @RequestMapping(
      value = "/hello/reactive",
      method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE
  )
  public Single<String> helloReactive(@RequestParam(value = "name", required = false) String name) {
    return new HelloObservableCommand(name).toObservable().toSingle();
  }

  @RequestMapping(
      value = "/hello/service",
      method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE
  )
  public String helloService(@RequestParam(value = "name", required = false) String name) {
    return helloService.hello(name);
  }

}
