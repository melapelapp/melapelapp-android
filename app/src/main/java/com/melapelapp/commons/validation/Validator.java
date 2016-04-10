package com.melapelapp.commons.validation;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.util.Patterns.EMAIL_ADDRESS;
import static android.util.Patterns.PHONE;
import static java.lang.String.format;

/**
 * Created by mcamacho on 4/2/16.
 */
public class Validator {
    List<ValidationObject> validationObjects = new ArrayList<>();

    public static Validator getInstance() {
        return new Validator();
    }

    public Validator add(TextView view, ValidationTypes validationType, int min) {
        ValidationObject validationObject = new ValidationObject(view, validationType, min);
        validationObjects.add(validationObject);
        return this;
    }

    public Validator add(TextView view, ValidationTypes validationType, int min, int max) {
        ValidationObject validationObject = new ValidationObject(view, validationType, min, max);
        validationObjects.add(validationObject);
        return this;
    }

    public Validator add(TextView view, ValidationTypes validationType) {
        ValidationObject validationObject = new ValidationObject(view, validationType);
        validationObjects.add(validationObject);
        return this;
    }

    public boolean validate() {
        boolean valid = true;

        for (ValidationObject validationObject : validationObjects) {
            TextView textView = validationObject.getView();
            String text = textView.getText().toString();
            switch (validationObject.getType()) {
                case TEXT_MIN_LENGTH:
                    if (text.isEmpty() || text.length() < validationObject.getMin()) {
                        textView.setError(format("at least %d characters", validationObject.getMin()));
                        valid = false;
                    } else {
                        textView.setError(null);
                    }
                    break;
                case TEXT_MIN_MAX_LENGTH:
                    if (text.isEmpty() || text.length() < validationObject.getMin() || text.length() > validationObject.getMax()) {
                        textView.setError(format("between %d and %d alphanumeric characters",
                                validationObject.getMin(), validationObject.getMax()));
                        valid = false;
                    } else {
                        textView.setError(null);
                    }
                    break;
                case EMAIL_ADDRESS:
                    if (text.isEmpty() || !EMAIL_ADDRESS.matcher(text).matches()) {
                        textView.setError("enter a valid email address");
                        valid = false;
                    } else {
                        textView.setError(null);
                    }
                    break;
                case PASSWORD:
                    if (text.isEmpty() || text.length() < 4 || text.length() > 10) {
                        textView.setError("between 4 and 10 alphanumeric characters");
                        valid = false;
                    } else {
                        textView.setError(null);
                    }
                    break;
                case PHONE:
                    if (text.isEmpty() || !PHONE.matcher(text).matches()) {
                        textView.setError("enter a valid phone number");
                        valid = false;
                    } else {
                        textView.setError(null);
                    }
                    break;
            }

        }

        return valid;
    }

    private class ValidationObject {
        private TextView view;
        private ValidationTypes type;
        private int min;
        private int max;

        ValidationObject(TextView view, ValidationTypes type) {
            this.view = view;
            this.type = type;
        }

        ValidationObject(TextView view, ValidationTypes type, int min) {
            this(view, type);
            this.min = min;
        }

        ValidationObject(TextView view, ValidationTypes type, int min, int max) {
            this(view, type, min);
            this.max = max;
        }

        int getMin() {
            return min;
        }

        int getMax() {
            return max;
        }

        ValidationTypes getType() {
            return type;
        }

        TextView getView() {
            return view;
        }

    }

    public static enum ValidationTypes {
        EMAIL_ADDRESS,
        PASSWORD,
        PHONE,
        TEXT_MIN_LENGTH,
        TEXT_MIN_MAX_LENGTH,
    }
}
