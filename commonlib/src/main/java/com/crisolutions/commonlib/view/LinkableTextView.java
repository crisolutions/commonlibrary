package com.crisolutions.commonlib.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;

import com.crisolutions.commonlib.utils.UiUtils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LinkableTextView extends AppCompatTextView {
    private static final String PHONE_PATTERN_DEFINITION = "(\\+1[\\s.-]?|1[\\s.-]?)?([0-9]{3}|\\([0-9]{3}\\))" +
            "[\\s.-]?[0-9]{3}[\\s.-]?[0-9]{4}";

    private Pattern phonePattern;
    private boolean attemptPatternMatch;
    private Context context;

    public LinkableTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LinkableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * Initialize this text view.
     */
    protected void init() {
        setLinkTextColor(ContextCompat.getColor(context, android.R.color.primary_text_dark));

        try {
            phonePattern = Pattern.compile(PHONE_PATTERN_DEFINITION);
            if (phonePattern != null) {
                attemptPatternMatch = true;
            }
        } catch (PatternSyntaxException e) {
            attemptPatternMatch = false;
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null && text.length() > 0) {
            SpannableString content = new SpannableString(UiUtils.fromHtml(text.toString()));

            if (attemptPatternMatch) {
                boolean linked = Linkify.addLinks(content,
                        phonePattern, "tel:",
                        Linkify.sPhoneNumberMatchFilter,
                        Linkify.sPhoneNumberTransformFilter);

                if (linked) {
                    text = content;
                    type = BufferType.SPANNABLE;
                    super.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        }
        super.setText(text, type);
    }
}
