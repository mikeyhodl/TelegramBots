package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to change the title of a chat. Titles can't be changed for private chats.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * @apiNote In regular groups (non-supergroups), this method will only work if the ‘All Members Are Admins’ setting is off in the target group.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetChatTitle extends BotApiMethodBoolean {
    public static final String PATH = "setChatTitle";

    private static final String CHATID_FIELD = "chat_id";
    private static final String TITLE_FIELD = "title";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Required. New chat title, 1-128 characters

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title can't be empty", this);
        }
    }

    public static abstract class SetChatTitleBuilder<C extends SetChatTitle, B extends SetChatTitleBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public SetChatTitleBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
