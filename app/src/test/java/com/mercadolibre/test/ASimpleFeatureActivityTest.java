package com.mercadolibre.test;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.mercadolibre.android.testing.AbstractActivityTest;
import com.mercadolibre.pipsearch.android.app.MainActivity;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Config(manifest= Config.NONE)
public class ASimpleFeatureActivityTest extends AbstractActivityTest<MainActivity> {

    @Test
    public void testASimpleFeatureActivity_withExtra_receivesUrlExtra() {
        final String url = "url";
        final String urlTest = "urlTest";
        final Intent intent = new Intent();
        intent.putExtra(url, urlTest);
        final MainActivity aSimpleFeatureActivity = startActivity(MainActivity.class, intent).get();

        assertNotNull("The received URL must not be null", aSimpleFeatureActivity.getIntent().getExtras().getSerializable(url));
        assertEquals("The received URL is wrong", urlTest, aSimpleFeatureActivity.getIntent().getExtras().getString(url));
    }

    @NonNull
    @Override
    protected List<ActivityControllerFactory<MainActivity>> getActivityControllers() {

        final List<ActivityControllerFactory<MainActivity>> controllerFactories = new ArrayList<>();

        //Add one controller factory that creates the activity without a specific intent
        controllerFactories.add(bundle -> {
            final ActivityController<MainActivity> controller =
                    createActivity(MainActivity.class, bundle);
            //Assertions after activity created
            assertNotNull("Activity must NOT be null", controller.get());
            return controller;
        });

        return controllerFactories;
    }

}
