package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.entities.Rating;
import emp.event_management_platform.repo.AppUserRepository;
import emp.event_management_platform.repo.EventRepository;
import emp.event_management_platform.repo.RatingRepository;
import emp.event_management_platform.service.IRatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IRatingServiceImpl implements IRatingService {
    private final RatingRepository ratingRepository;
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public boolean AddRating(int rating, Long event_id, Long user_id) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        Event event = eventRepository.findById(event_id).orElse(null);
        AppUser user = appUserRepository.findById(String.valueOf(user_id)).orElse(null);
        if (event == null || user == null) {
            return false;
        }
        Rating newRating = Rating.builder()
                .user(user)
                .event(event)
                .rating(rating)
                .build();
        ratingRepository.save(newRating);
        return true;

    }
}
