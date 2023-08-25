package hooks;


import io.cucumber.java.Before;

import static base_url.MedunnaBaseUrl.setup;

public class Hooks {

    @Before
    public void beforeApi(){
        setup();
    }

}
