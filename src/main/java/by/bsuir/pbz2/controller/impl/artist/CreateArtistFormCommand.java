package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreateArtistFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/artist/create_artist_form.jsp";
    }
}
