package work.gaigeshen.shiro.demo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gaigeshen
 */
@RequestMapping("/documents")
@RestController
public class DocumentController {

  @RequiresPermissions("documents:list")
  @GetMapping("/list")
  public Object listDocuments() {
    return "the documents content you can list";
  }
}
