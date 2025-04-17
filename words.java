public class words {
   
   private int count;
   private String word;
   //this object class holds the count of the word and the name of the word
   
   public words(String curWord) {
      word = curWord;
      count = 1;
   }  //the creation of the word starts with a count of 1
   
   public void repeated() {
      count++;
   }  //whenever that word is found later in the book it will be repeated and add 1 to the count
   
   public String getWord() {
      return word;
   }  //this finds the name of the word that is the object that is being looked at
   
   public int getCount() {
      return count;
   }  //this returns the count that the word object has
   
   public String toString() {
      return  word + " " + count + "";
   }  //this makes it easier to print the word and the count of that word object
   
   
}