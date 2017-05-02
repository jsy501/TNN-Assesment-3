package me.lihq.game.gui.speechbubbles;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import me.lihq.game.AssetLoader;
import me.lihq.game.GameWorld;
import me.lihq.game.models.Clue;
import me.lihq.game.people.QuestionStyle;

/**
 * EXTENDED
 * specialised speech bubble for displaying questioning style available and take user input.
 */

public class QuestionStyleSelectionBubble extends SpeechBubble{
    public QuestionStyleSelectionBubble(GameWorld gameWorld, Clue selectedClue, AssetLoader assetLoader) {
        super(gameWorld.getPlayer(), assetLoader);

        TextButton aggressiveButton = new TextButton("Aggressive", getSkin(), "buttonBubble");
        aggressiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameWorld.getConversationManager().addSpeechBubble(gameWorld.getPlayer(), gameWorld.getPlayer().
                        getDialogue().getQuestionDialogue(QuestionStyle.AGGRESSIVE));
                gameWorld.getInteraction().question(selectedClue, QuestionStyle.AGGRESSIVE);
            }
        });

        TextButton neutralButton = new TextButton("Neutral", getSkin(), "buttonBubble");
        neutralButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameWorld.getConversationManager().addSpeechBubble(gameWorld.getPlayer(), gameWorld.getPlayer().
                        getDialogue().getQuestionDialogue(QuestionStyle.NEUTRAL));
                gameWorld.getInteraction().question(selectedClue, QuestionStyle.NEUTRAL);
            }
        });

        TextButton niceButton = new TextButton("Nice", getSkin(), "buttonBubble");
        niceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameWorld.getConversationManager().addSpeechBubble(gameWorld.getPlayer(), gameWorld.getPlayer().
                        getDialogue().getQuestionDialogue(QuestionStyle.NICE));
                gameWorld.getInteraction().question(selectedClue, QuestionStyle.NICE);
            }
        });

        TextButton cancelButton = new TextButton("Cancel", getSkin(), "buttonBubble");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameWorld.getConversationManager().addSpeechBubble(gameWorld.getPlayer(), "Never mind.");
                gameWorld.getConversationManager().nextSpeechBubble();
            }
        });

        // depending on the player's current personality meter the set of available questioning styles changes
        switch (gameWorld.getPlayer().getPersonality()){
            case NICE:
                addButton(niceButton);
                addButton(neutralButton);
                addButton(cancelButton);
                break;

            case NEUTRAL:
                addButton(niceButton);
                addButton(neutralButton);
                addButton(aggressiveButton);
                addButton(cancelButton);
                break;

            case AGGRESSIVE:
                addButton(neutralButton);
                addButton(aggressiveButton);
                addButton(cancelButton);
                break;
        }
    }

    private void addButton(TextButton textButton){
        /*
        EXTENDED CODE START
         */
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                assetLoader.menuClick.play();
            }
        });

        /*
        EXTENDED CODE END
         */

        getContentTable().add(textButton).row();
    }
}
