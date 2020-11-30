package services;

public class LessonService {

    private AuthenticationService authenticationService;

    public LessonService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
