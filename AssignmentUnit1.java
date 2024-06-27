import java.util.Scanner;
import java.util.stream.Stream;
import java.util.HashMap;
public class AssignmentUnit1 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter any paragraph or lengthy text: ");
        String text = scanner.nextLine();
        AnalyzeText(text);
        while (true) {
            System.out.println("Menu Options:");
            System.out.println("1. Check the frequency of occurrences of any character. \n2. Check the frequency of occurrences of any word. \n3. Exit");
            System.out.println("Enter your choice: ");
            try{
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    System.out.println("Enter the character to check frequency: ");
                    char ch = scanner.nextLine().charAt(0);
                    checkCharFreq(text, ch);
                    break;
                case 2:
                    System.out.println("Enter the word to check frequency: ");
                    String word = scanner.nextLine();
                    checkWordFreq(text, word);
                    break;
                case 3:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. choose between 1-3.");
            }
            }catch(Exception e){
                try{
                    scanner.nextLine();
                }catch(Exception e1){
                }finally{
                    System.out.println("Invalid input. Please enter a valid choice.");
                }
                
            }
        }
    }

    public static void AnalyzeText(String text){
        int charCount = text.length();
        System.out.println("Number of characters: " + charCount);

        String[] words = text.split(" ");
        int wordCount = words.length;
        System.out.println("Number of words: " + wordCount);

        int uniqueWords = Stream.of(words).distinct().toArray(String[]::new).length;
        System.out.println("Number of unique words: " + uniqueWords);


        HashMap<Character, Integer> charFreq = new HashMap<>();
        for(int i = 0; i < charCount; i++){
            char ch = text.toUpperCase().charAt(i);
            if(ch == ' '){
                continue;
            }
            if(charFreq.containsKey(ch)){
                charFreq.put(ch, charFreq.get(ch) + 1);
            }else{
                charFreq.put(ch, 1);
            }
        }
        int maxFreq = 0;
        char maxFreqChar = ' ';
        for(Character ch: charFreq.keySet()){
            if(charFreq.get(ch) > maxFreq){
                maxFreq = charFreq.get(ch);
                maxFreqChar = ch;
            }
        }
        System.out.println("Character with maximum frequency: " + maxFreqChar + " with frequency: " + maxFreq);
    }


    public static void checkCharFreq(String text, char ch){
        int charCount = text.length();
        int freq = 0;
        for(int i = 0; i < charCount; i++){
            if(text.charAt(i) == ch){
                freq++;
            }
        }
        System.out.println("Frequency of character " + ch + " is: " + freq);
    }
    public static void checkWordFreq(String text, String word){
        String[] words = text.split(" ");
        int wordCount = words.length;
        int freq = 0;
        for(int i = 0; i < wordCount; i++){
            if(words[i].equals(word)){
                freq++;
            }
        }
        System.out.println("Frequency of word " + word + " is: " + freq);
    }
}
