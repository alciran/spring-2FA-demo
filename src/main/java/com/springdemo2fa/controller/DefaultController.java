package com.springdemo2fa.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.springdemo2fa.model.UserDto;
import com.springdemo2fa.service.UserService;

@Controller
public class DefaultController {

    @Autowired
    private UserService userDtoService;

    @GetMapping("/")
    public ModelAndView showHomepage(Principal principal) {
        if (principal == null) {
            return new ModelAndView("home");
        } else {
            return new ModelAndView("home", "user", principal);
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/user/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/user/register")
    public ModelAndView registerNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors())
            return new ModelAndView("register", "user", userDto);

        if (userDto.getPassword().length() < 8) {
            result.rejectValue("password", "password.min", "The password size must be more than 8 characters!");
            return new ModelAndView("register");
        }

        try {
            userDtoService.creatUser(userDto);
            redirect.addFlashAttribute("user", userDto);
            return new ModelAndView("redirect:/user/registered");
        } catch (EntityExistsException ex) {
            userDtoService.loadUserByUsername(userDto.getUsername());
            result.rejectValue("username", "already.exists");
            return new ModelAndView("register");
        }
    }

    @GetMapping("/user/registered")
    public String showRegisteredPage(Model model) throws UnsupportedEncodingException {
        UserDto userDto = (UserDto) (model.asMap().get("user"));

        if (userDto == null)
            return "redirect:/user/register";

        String otpUrl = OTP.getURL(userDto.getMfaSecret(), 6, Type.TOTP, userDto.getMfaDescription(),
                userDto.getUsername());
        String QrCodeUrl = String.format(
                "https://chart.googleapis.com/chart?cht=qr&chs=200x200&chl=%s",
                URLEncoder.encode(otpUrl, "UTF-8"));

        model.addAttribute("QrCodeUrl", QrCodeUrl);

        return "registered";
    }

}
