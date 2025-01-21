package org.senla.controller.movies;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieAddController {
    private MovieService movieService;

    @GetMapping("/add")
    public String showMoviePage(Model model) {
        model.addAttribute("movieAddDto", new MovieAddDto());
        return "movies/add";
    }

    @PostMapping("/add")
    public String processAddMovie(
            @Valid @ModelAttribute("movieAddDto") MovieAddDto movieAddDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "movies/add";
        }

        try {
            List<MemberDto> members = new ArrayList<>();
            if (movieAddDto.getMembers() != null) {
                for (int i = 0; i < movieAddDto.getMembers().size(); i++) {
                    MemberDto memberDto = movieAddDto.getMembers().get(i);

                    memberDto.setType(MemberType.valueOf(movieAddDto.getMembers().get(i).getType().toString().toUpperCase()));
                    memberDto.setGender(GenderType.valueOf(movieAddDto.getMembers().get(i).getGender().toString().toUpperCase()));

                    members.add(memberDto);
                }
            }

            movieAddDto.setMembers(members);
            movieService.addNewMovie(movieAddDto);

            return "redirect:/movies/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "movies/add";
        }

    }
}
