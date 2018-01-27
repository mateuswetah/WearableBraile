package mateuswetah.wearablebraille.BrailleÉcran;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import mateuswetah.wearablebraille.R;

/**
 * Created by mateus on 9/1/15.
 */
public class BrailleDots {

    // Create an array of six initially invisible OutputDots and associate them with the XML
    public final ImageButton ButtonDots[] = new ImageButton[6];

    // An array that stores the 44 possible summations.
    private ArrayList<Integer> SummedValueDots = new ArrayList<Integer>();

    // List of 44 Dots Objects, that hold the information read from XML
    private List<Dots> DotsList;

    // Tone Generator, Vibrator and TextToSpeech for feedback
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
    private Vibrator vibrator = null;
    private TextToSpeech dotTTS;

    // Access to database for loading settings
    public static final String PREFS_NAME = "SettingsFile";
    public SharedPreferences settings;

    // Flags to deal with feedback
    boolean useVibrationPatterns = true;
    boolean useToneGenerator = true;
    boolean useDotNumberSpeaker = false;

    private WearableActivity activity;
    private int nSymbols = 50; // Number of symbols listed in the XML

    // Constructor

    // Constructor
    public BrailleDots(WearableActivity activity, boolean reversedLines) {

        this.activity = activity;

        // Associate view elements
        this.createDotButtons(reversedLines);

        // Sets tts and vibration service
        vibrator = (Vibrator) this.activity.getSystemService(Context.VIBRATOR_SERVICE);
        dotTTS = new TextToSpeech(this.activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                Log.d("TTS", "TextToSpeech Service Initialized");
                //tts.setLanguage(Locale.ENGLISH);
            }
        });

        // Sets Data Storage
        settings = activity.getSharedPreferences(PREFS_NAME, 0);
        this.loadSettings();

        // Uses the XMLPullParser to fill the Dots list with Dots Objects
        DotsList = DotsXMLPullParser.getStaticDotsFromFile(activity);

        // Fill the array with the 45 possible summations
        for (int i = 0; i < nSymbols; i++) {
            SummedValueDots.add(DotsList.get(i).getSumValue());
        }
    }

    public BrailleDots(WearableActivity activity) {
       this(activity, false);
    }

    // Appear or disappear with the Dots
    public boolean toggleDotVisibility(int i){

        if (((Boolean)ButtonDots[i].getTag()) == false) {
            this.setDotVisibility(i,true);
            return true;
        } else {
            this.setDotVisibility(i,false);
            return false;
        }
    }

    // Change dot visibility, regardless of it's current state.
    public void setDotVisibility(int i, boolean value) {

        if (value == false) {
            ButtonDots[i].setTag(new Boolean(false));
            ButtonDots[i].setImageDrawable(activity.getDrawable(R.drawable.dot_unactive));
            switch (i) {
                case 0:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_CDMA_SIGNAL_OFF, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50}, -1);
                    else this.vibrator.vibrate(100);
                    //if (useDotNumberSpeaker) this.dotTTS.speak("1", TextToSpeech.QUEUE_FLUSH, null, "Dot 1");
                    break;
                case 1:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_CDMA_SIGNAL_OFF, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    //if (useDotNumberSpeaker) this.dotTTS.speak("2", TextToSpeech.QUEUE_FLUSH, null, "Dot 2");
                    break;
                case 2:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_CDMA_SIGNAL_OFF, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    //if (useDotNumberSpeaker) this.dotTTS.speak("3", TextToSpeech.QUEUE_FLUSH, null, "Dot 3");
                    break;
                case 3:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_CDMA_SIGNAL_OFF, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50,30,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    //if (useDotNumberSpeaker) this.dotTTS.speak("4", TextToSpeech.QUEUE_FLUSH, null, "Dot 4");
                    break;
                case 4:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_CDMA_SIGNAL_OFF, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50,30,50,30,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    //if (useDotNumberSpeaker) this.dotTTS.speak("5", TextToSpeech.QUEUE_FLUSH, null, "Dot 5");
                    break;
                case 5:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_CDMA_SIGNAL_OFF, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,25,50,25,50,25,50,25,50},-1);
                    else this.vibrator.vibrate(100);
                    //if (useDotNumberSpeaker) this.dotTTS.speak("6", TextToSpeech.QUEUE_FLUSH, null, "Dot 6");
                    break;
            }

        } else {
            ButtonDots[i].setTag(new Boolean(true));
            ButtonDots[i].setImageDrawable(activity.getDrawable(R.drawable.dot_active));
            switch (i) {
                case 0:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_DTMF_1, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50}, -1);
                    else this.vibrator.vibrate(100);
                    if (useDotNumberSpeaker) this.dotTTS.speak("1", TextToSpeech.QUEUE_ADD, null, "Dot 1");
                    break;
                case 1:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_DTMF_2, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    if (useDotNumberSpeaker) this.dotTTS.speak("2", TextToSpeech.QUEUE_ADD, null, "Dot 2");
                    break;
                case 2:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_DTMF_3, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    if (useDotNumberSpeaker) this.dotTTS.speak("3", TextToSpeech.QUEUE_ADD, null, "Dot 3");
                    break;
                case 3:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_DTMF_4, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50,30,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    if (useDotNumberSpeaker) this.dotTTS.speak("4", TextToSpeech.QUEUE_ADD, null, "Dot 4");
                    break;
                case 4:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_DTMF_5, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,30,50,30,50,30,50,30,50},-1);
                    else this.vibrator.vibrate(100);
                    if (useDotNumberSpeaker) this.dotTTS.speak("5", TextToSpeech.QUEUE_ADD, null, "Dot 5");
                    break;
                case 5:
                    if (useToneGenerator) toneGenerator.startTone(ToneGenerator.TONE_DTMF_6, 100);
                    if (useVibrationPatterns) this.vibrator.vibrate(new long[]{0,50,25,50,25,50,25,50,25,50},-1);
                    else this.vibrator.vibrate(100);
                    if (useDotNumberSpeaker) this.dotTTS.speak("6", TextToSpeech.QUEUE_ADD, null, "Dot 6");
                    break;
            }
        }
    }

    // Disappear all the Dots
    public void toggleAllDotsOff() {

        for (int i = 0; i < 6; i++) {
            ButtonDots[i].setImageDrawable(activity.getDrawable(R.drawable.dot_unactive));
            ButtonDots[i].setTag(new Boolean(false));
        }

    }

    // Called in MainActivity every time a Dot is added or removed.
    public String checkCurrentCharacter(boolean CapsOn, boolean tmpCapsOn, boolean NumOn, boolean tmpNumOn) {

        String latimOutput = "";
        int currentSum = 0, indexLatimOutput = 0;

        // Reads the current visibility of the dots and associates it with a summed value
        for (int i = 0; i < 6; i++) {
            if (((Boolean)ButtonDots[i].getTag()) == true)
                currentSum += Math.pow(2, i);
        }

        // Looks for which index of the SummedValuesDots vector equals the sum of the current Dots
        for (; indexLatimOutput < nSymbols; indexLatimOutput++) {
            if (SummedValueDots.get(indexLatimOutput) == currentSum) {
                break;
            }
        }
        //Log.d("LATIM_OUTPUT", "Current: " + indexLatimOutput);
        // Gets from the Dots object the relation between the found summation and a Symbol
        // the indexLatimOutput is checked to see if it's a valid character.
        if (indexLatimOutput < nSymbols) {
            latimOutput = DotsList.get(indexLatimOutput).getDotSymbol();

            // Removing Settings from the list, making it an Invalid Character
            if (latimOutput.equals("Cf") && CapsOn == false && tmpCapsOn == false)
                latimOutput = "";

            // Removing Help from the list, making it an Invalid Character
            if (latimOutput.equals("?!") && NumOn == false && tmpNumOn == false)
                latimOutput = "";

            // Apply the flags changes:
            if (CapsOn || tmpCapsOn)
                if (latimOutput != " ") // workaround, uoUpperCase() seems to remove white space
                    latimOutput = latimOutput.toUpperCase();

            if (NumOn || tmpNumOn) {

                switch (latimOutput)
                {
                    case "a":
                        latimOutput = "1";
                        break;
                    case "b":
                        latimOutput = "2";
                        break;
                    case "c":
                        latimOutput = "3";
                        break;
                    case "d":
                        latimOutput = "4";
                        break;
                    case "e":
                        latimOutput = "5";
                        break;
                    case "f":
                        latimOutput = "6";
                        break;
                    case "g":
                        latimOutput = "7";
                        break;
                    case "h":
                        latimOutput = "8";
                        break;
                    case "i":
                        latimOutput = "9";
                        break;
                    case " ":
                        latimOutput = " ";
                        break;
                    case "j":
                        latimOutput = "0";
                        break;
                    case "A":
                        latimOutput = "1";
                        break;
                    case "B":
                        latimOutput = "2";
                        break;
                    case "C":
                        latimOutput = "3";
                        break;
                    case "D":
                        latimOutput = "4";
                        break;
                    case "E":
                        latimOutput = "5";
                        break;
                    case "F":
                        latimOutput = "6";
                        break;
                    case "G":
                        latimOutput = "7";
                        break;
                    case "H":
                        latimOutput = "8";
                        break;
                    case "I":
                        latimOutput = "9";
                        break;
                    case "J":
                        latimOutput = "0";
                        break;
                    case ".":
                        latimOutput = ".";
                        break;
                    case "-":
                        latimOutput = "-";
                        break;
                    case "?":
                        latimOutput = "?";
                        break;
                    case "!":
                        latimOutput = "!";
                        break;
                    case "%":
                        latimOutput = "%";
                        break;
                    case ",":
                        latimOutput = ",";
                        break;
                    case ":":
                        latimOutput = ":";
                        break;
                    case "Cf":
                        latimOutput = "Cf";
                        break;
                    case "CF":
                        latimOutput = "Cf";
                        break;
                    case "?!":
                        latimOutput = "?!";
                        break;
                    default:
                        latimOutput = "In";
                        break;
                }
            }

        }

        Log.d("BRAILLE_DOTS", "Output: " + latimOutput);
        return latimOutput;
    }

    // Instantiate dot buttons
    public void createDotButtons (boolean isLineReversed) {
        // To be used in Orientation-landscape mode for layouts such as Perkins
        if (isLineReversed) {
            ButtonDots[2] = (ImageButton) activity.findViewById(R.id.dotButton1);
            ButtonDots[1] = (ImageButton) activity.findViewById(R.id.dotButton2);
            ButtonDots[0] = (ImageButton) activity.findViewById(R.id.dotButton3);
            ButtonDots[5] = (ImageButton) activity.findViewById(R.id.dotButton4);
            ButtonDots[4] = (ImageButton) activity.findViewById(R.id.dotButton5);
            ButtonDots[3] = (ImageButton) activity.findViewById(R.id.dotButton6);
        } else {
            ButtonDots[0] = (ImageButton) activity.findViewById(R.id.dotButton1);
            ButtonDots[1] = (ImageButton) activity.findViewById(R.id.dotButton2);
            ButtonDots[2] = (ImageButton) activity.findViewById(R.id.dotButton3);
            ButtonDots[3] = (ImageButton) activity.findViewById(R.id.dotButton4);
            ButtonDots[4] = (ImageButton) activity.findViewById(R.id.dotButton5);
            ButtonDots[5] = (ImageButton) activity.findViewById(R.id.dotButton6);

        }
        for (int i = 0; i < 6; i++) {
            ButtonDots[i].setTag(new Boolean(false));
            ButtonDots[i].setImageDrawable(activity.getDrawable(R.drawable.dot_unactive));
        }
    }

    // Load settings from database
    public void loadSettings() {
        this.useDotNumberSpeaker = settings.getBoolean("useDotNumberSpeaking", false);
        this.useVibrationPatterns = settings.getBoolean("useVibrationPatterns", false);
        this.useToneGenerator = settings.getBoolean("useToneGenerator", false);
    }

    // To be used on activitie's onDestroy()
    public void freeTTSService() {
        this.dotTTS.stop();
        this.dotTTS.shutdown();
    }
}
