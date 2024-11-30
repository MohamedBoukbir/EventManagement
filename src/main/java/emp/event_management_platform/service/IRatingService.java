package emp.event_management_platform.service;

import emp.event_management_platform.entities.Rating;

public interface IRatingService {
    public boolean AddRating(int rating, Long event_id, Long user_id);
}
