package org.telegram.telegrambots.meta.api.objects.stickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 6.6
 *
 * This object describes a sticker to be added to a sticker set.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputSticker implements BotApiObject, Validable {

    private static final String STICKER_FIELD = "sticker";
    private static final String EMOJI_LIST_FIELD = "emoji_list";
    private static final String MASK_POSITION_FIELD = "mask_position";
    private static final String KEYWORDS_FIELD = "keywords";
    private static final String FORMAT_FIELD = "format";

    /**
     * The added sticker.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a
     * String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data.
     * Animated and video stickers can't be uploaded via HTTP URL.
     */
    @JsonProperty(STICKER_FIELD)
    @NonNull
    private InputFile sticker;
    /**
     * List of 1-20 emoji associated with the sticker
     */
    @JsonProperty(EMOJI_LIST_FIELD)
    @NonNull
    @Singular("emoji")
    private List<String> emojiList;
    /**
     * Format of the added sticker,
     * must be one of “static” for a .WEBP or .PNG image, “animated” for a .TGS animation, “video” for a WEBM video
     */
    @JsonProperty(FORMAT_FIELD)
    @NonNull
    private String format;
    /**
     * Optional.
     * Position where the mask should be placed on faces. For “mask” stickers only.
     */
    @JsonProperty(MASK_POSITION_FIELD)
    private MaskPosition maskPosition;
    /**
     * Optional.
     * List of 0-20 search keywords for the sticker with total length of up to 64 characters.
     * For “regular” and “custom_emoji” stickers only.
     */
    @JsonProperty(KEYWORDS_FIELD)
    @Singular
    private List<String> keywords;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (emojiList.isEmpty() || emojiList.size() > 20) {
            throw new TelegramApiValidationException("Emoji list must have between 1 and 20 items", this);
        }

        if (keywords != null && keywords.size() > 20) {
            throw new TelegramApiValidationException("Keywords list must have between 0 and 20 items", this);
        }
        if (!Arrays.asList("static", "animated", "video").contains(format)) {
            throw new TelegramApiValidationException("Format must be 'static', 'animated', 'video'", this);
        }

        if (maskPosition != null) {
            maskPosition.validate();
        }

        sticker.validate();
    }
}
