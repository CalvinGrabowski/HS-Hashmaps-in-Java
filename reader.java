/* Calvin Grabowski
   Advance Topics 7th period
   file assignment #2
*/

import java.io.*;
import java.util.*;

public class reader {

   public static void main(String[] args) throws FileNotFoundException {

      Scanner line = new Scanner(new File("file.txt"));
      Scanner sc = new Scanner(System.in);
      ArrayList<words> text = new ArrayList<words>();
      // I take in the book and split it up into lines I create a scanner for user
      // input and I make an arraylist of my words object
      // the words object holds a word's name and the amount of times it has shown up
      // in the book

      int breakAfter = 0;
      while (line.hasNext()) {
         if (line.nextLine().equals("CHAPTER 1. Loomings.")) {
            breakAfter++;
         }
         if (breakAfter > 1) {
            break;
         }
      }
      // method finds the line chapter one skips it then finds it the second time it
      // happens and starts reading after this occurance

      // this goes through the whole book and tracks the words until there are no more
      // words
      while (line.hasNext()) {

         // this gets rid of all dashes and splits the word up. Example: ago—never to ago
         // never and line-to-go = line to go
         String curLine = line.nextLine().toLowerCase();
         Scanner newWord = new Scanner(curLine);
         // curLine holds the lines of the book
         // newWord holds the words on a line

         boolean chapter = false;
         while (newWord.hasNext()) {
            // loops every word and that word will be currentWord
            String currentWord = newWord.next();
            currentWord = currentWord.replaceAll("[^a-z]", "");
            // removes all symbols and only keeps words in lowercase letters

            // skips the line if it says chapter
            if (currentWord.equals("chapter")) {
               chapter = true;
            } else if (!chapter) {

               boolean inBook = false;
               for (int i = 0; i < text.size(); i++) {
                  if (text.get(i).getWord().equals(currentWord)) {
                     text.get(i).repeated();
                     inBook = true;
                  }
               }
               words curWord = new words(currentWord);
               // sees if the word is already in the book Dictionary and if it is it will count
               // one to that word count
               // if it isn't in the book Dictionary then it will add it as a new word

               if (!inBook) {
                  if (!currentWord.equals("")) {
                     text.add(curWord);
                  }
               }

            }

         }
      } // this is the end of processing all the words in the book and adding them to
        // the dictionary

      /////////////////////////////////// begins sorting
      /////////////////////////////////// ///////////////////////////////////

      words notSorted[] = new words[text.size()];
      words alphaSort[] = new words[text.size()];
      words sorted[] = new words[text.size()];
      // makes three arrays so I can keep each sorted method in its own array so you
      // can call them seperately without resorting
      for (int i = 0; i < notSorted.length; i++) {
         alphaSort[i] = text.get(i);
         notSorted[i] = text.get(i);
         sorted[i] = text.get(i);
      }

      System.out.println("And now I am going to sort in two different ways.");

      long timer = System.currentTimeMillis();
      System.out.println("Alphabetical Sort in milliseconds");
      reader.alphaSort(alphaSort);
      System.out.println(System.currentTimeMillis() - timer);
      // alphaSort is unsorted being sorted in alphabetical order

      timer = System.currentTimeMillis();
      System.out.println("Number Sort in milliseconds");
      reader.sort(sorted);
      System.out.println(System.currentTimeMillis() - timer);
      // sorted is unsorted being sorted in largest occurance

      System.out.println("This is the top ten reoccuring words in the book:");
      System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
      for (int i = 0; i <= 10; i++) {
         System.out.println(sorted[i]);
      }
      System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
      System.out.println("The total amount of different words is " + sorted.length);
      // this shows the top ten reoccurring words

      // this is a while loop that repeatedly finds the words that the user is trying
      // to find until the user types "no more"
      boolean searching = true;

      while (searching == true) {
         System.out.println("\nDo you want to find anyword? If so type it. If not type \"no more\"");
         String answer = sc.nextLine().toLowerCase();
         if (answer.equals("no more")) {
            break;
         }
         timer = System.nanoTime();
         System.out.println("Sequential Search");
         reader.sequentialSearch(sorted, answer);
         System.out.println(" and it takes " + (System.nanoTime() - timer) + " nano seconds");
         // this records the time and speed of the searches while also giving the output
         // of what word they found
         timer = System.nanoTime();
         System.out.println("\nBinary Search");
         reader.binarySearch(alphaSort, answer);
         System.out.println(" and it takes " + (System.nanoTime() - timer) + " nano seconds");
      }
      // this finds the word the user is looking for and response with how much it
      // occurs or that the word isn't there

      // this makes all of the words go into a text file

      PrintStream output = new PrintStream(new File("output.txt"));

      reader.superSort(notSorted);
      // this sorts it by occurances in alphabetical order
      for (int i = 0; i < alphaSort.length; i++) {
         output.println(notSorted[i]);
      }

      Map<String, Double> newText = new HashMap<String, Double>();
      Scanner planner = new Scanner(new File("output.txt"));

      while (planner.hasNextLine()) {

      }

   } // main class ends

   ///////////////////////////////////////////// super sort
   ///////////////////////////////////////////// //////////////////////////////////////////////////////////

   public static words[] superSort(words list[]) {

      words current;
      int index = 1;
      while (index < list.length) {
         // index keeps track of where the current changing word came from. Current is
         // the word that is changing. Now is the place where the word is now
         current = list[index];
         int now = index - 1;

         while (now >= 0) {
            // goes from the right side of array to the left until it runs into a words that
            // has occured more or just as much as it has
            if (current.getCount() > list[now].getCount()) {
               list[now + 1] = list[now];
               now--;
            } // after it finds the same occurances it begins to sort it alphabetical but
              // breaks if it finds something with a different count
            else if (current.getCount() == list[now].getCount()
                  && (0 > current.getWord().compareTo(list[now].getWord()))) {
               list[now + 1] = list[now];
               now--;
            } else {
               break;
            } // breaks when the word is both in the right order in occurances and
              // alpahbetical
         }
         // places the word into the list then moves on from where the word was
         // originally
         list[now + 1] = current;
         index++;
      }

      return list;
   }
   // I have to create two sorts and two searching methods

   ////////////////////////////////////////////////// super sort
   ////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////

   // this sorts it by word count
   public static words[] sort(words[] unsorted) {

      words current;

      for (int index = 1; index < unsorted.length; index++) {

         current = unsorted[index];
         int now = index - 1;
         // now holds where the moving word is looking at
         // current is the moving word
         // index holds where the moving word was taken from
         while (now >= 0 && current.getCount() > unsorted[now].getCount()) {
            unsorted[now + 1] = unsorted[now];
            now--;
         } // grabs words from left to right and compares them from the right to the left
           // of the sorted words
         unsorted[now + 1] = current;
      }
      return unsorted;
   }

   // this sorts it alphabetically
   public static words[] alphaSort(words aSort[]) {

      // copies the sorted array and sorts it by letters rather than word count
      // this is a terrible sorting method that swaps a word at each position once for
      // one for loop and then it loops through the whole for loop and swaps until
      // everything is put into place
      words placeHolder;
      for (int i = 0; i < aSort.length; i++) {
         for (int j = i; j < aSort.length; j++) {
            if (0 < aSort[i].getWord().compareTo(aSort[j].getWord())) {
               placeHolder = aSort[i];
               aSort[i] = aSort[j];
               aSort[j] = placeHolder;
            }
            // this sorts it from # -> a-z -> special letters
         }
      }
      return aSort;
   }

   public static void sequentialSearch(words list[], String answer) {
      // this takes the list of words, preferably an occurances list (even though it
      // works with any unsorted list), and the word that the user is trying to look
      // for
      // it then goes from left to right to find the word that the person is looking
      // for and sends out if it finds it and how many occurances it has
      boolean wordThere = false;
      for (int i = 0; i < list.length; i++) {
         if (list[i].getWord().equals(answer)) {
            System.out.println(list[i]);
            wordThere = true;
         }
      }
      if (!wordThere) {
         System.out.println("This word does not exist in this book");
      }

   }

   public static void binarySearch(words list[], String answer) {
      // this takes a sorted alphabetical list and the user input to see if a word is
      // in there
      boolean wordThere = false;
      int min = 0;
      int max = list.length;
      int mid;
      // it records a min max and middle to try so it can go to the center and see if
      // that is greater than or less than the word it is looking for
      // if it is greater than then it will make the min equal the mid and it will
      // shift the area it is searching for into half the list
      while (true) {
         mid = ((max + min) / 2);
         if (list[mid].getWord().equals(answer)) {
            System.out.println(list[mid]);
            break;
         } // if the center is the answer then the word is there
         if (max == mid || min == mid) {
            System.out.println("This word does not exist in this book");
            break;
         } // if in the center the word isn't there and the list has been searched then it
           // isn't there
         if (0 < list[mid].getWord().compareTo(answer)) {
            max = mid;
         }
         if (0 > list[mid].getWord().compareTo(answer)) {
            min = mid;
         }

      }

   }

}

// original remove method
