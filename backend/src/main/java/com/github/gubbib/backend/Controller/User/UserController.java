package com.github.gubbib.backend.Controller.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/users")
public class UserController {

}
