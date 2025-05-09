package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodSerializable;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to edit captions of messages.
 * On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @apiNote Note that business messages that were not sent by the bot and do not contain an inline keyboard
 * can only be edited within 48 hours from the time they were sent.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditMessageCaption extends BotApiMethodSerializable {
    public static final String PATH = "editmessagecaption";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String CAPTION_FIELD = "caption";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    private static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    /**
     * Required if inline_message_id is not specified. Unique identifier for the chat to send the
     * message to (Or username for channels)
     */
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId;
    /**
     * Optional.
     * New caption of the message
     */
    @JsonProperty(CAPTION_FIELD)
    private String caption;
    /**
     * Optional.
     * A JSON-serialized object for an inline keyboard.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    /**
     * Optional.
     * Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of parse_mode
     */
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> captionEntities;
    /**
     * Optional.
     * Pass True, if the caption must be shown above the message media
     */
    @JsonProperty(SHOW_CAPTION_ABOVE_MEDIA_FIELD)
    private Boolean showCaptionAboveMedia;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message to be edited was sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;

    @Tolerate
    public void setChatId(Long chatId) {
        this.chatId = chatId == null ? null : chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseMessageOrBoolean(answer);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inlineMessageId == null) {
            if (chatId == null || chatId.isEmpty()) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chatId != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (messageId != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static class EditMessageCaptionBuilder {

        @Tolerate
        public EditMessageCaptionBuilder chatId(Long chatId) {
            this.chatId = chatId == null ? null : chatId.toString();
            return this;
        }
    }
}
