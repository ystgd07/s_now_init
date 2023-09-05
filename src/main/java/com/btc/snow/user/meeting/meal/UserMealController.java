package com.btc.snow.user.meeting.meal;

import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Controller
@RequestMapping("/user/meal")
public class UserMealController {

    @Autowired
    UserMealService userMealService;

    @Autowired
    MealUploadFileService mealUploadFileService;

    @PostMapping("/write_meal")
    public String writeMeal(UserMealDto userMealDto,
                            @RequestParam(value = "file", required = false) MultipartFile file,
                            HttpSession session) {
        log.info("writeMeal()");

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserDto");

        String nextPage = "redirect:/user/meeting/list";

        String saveFileName = mealUploadFileService.upload(file, loginedUserDto.getId());

        if (saveFileName != null) {

            userMealDto.setUser_id(loginedUserDto.getId());
            userMealDto.setImg(saveFileName);

            int result = userMealService.writeMeal(userMealDto);

            if (result <= 0) {
                log.info("식사 등록 실패입니다.");
            }

        } else {

            log.info("식사 등록에 실패입니다.");
        }
        return nextPage;
    }

    @GetMapping("/meal_detail")
    public String mealDetail(Model model, @RequestParam("no") int no) {
        log.info("mealDetail()");

        String nextPage = "user/meeting/meal_detail";

        UserMealDto userMealDto = userMealService.mealDetail(no);


        model.addAttribute("userMealDto", userMealDto);

        return nextPage;
    }

}
