package sk.tuke.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

import javax.persistence.NoResultException;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    private RatingService ratingService;

    public RatingServiceRest(RatingService ratingService){
        this.ratingService = ratingService;
    }


    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game) {

        return ratingService.getAverageRating(game);
    }

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game,player);
    }

}
