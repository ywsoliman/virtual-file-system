public class Main {
    // System.out.println();
    public static void main(String[] args) {

        Directory root = new Directory("root");

        File rootFile = new File("test.pdf");
        root.addFile(rootFile);

        Directory sub1 = new Directory("folder1");
        Directory sub2 = new Directory("folder2");

        root.addSubDirectory(sub1);
        root.addSubDirectory(sub2);

        File file1 = new File("welly.txt");
        File file2 = new File("youssef.txt");

        sub1.addFile(file1);
        sub2.addFile(file2);


        Directory sub3 = new Directory("FINAL");

        root.addSubDirectory(sub3);

        File file3 = new File("V1.txt");
        File file4 = new File("V2.txt");

        sub3.addFile(file3);
        sub3.addFile(file4);

        Directory insideSub3 = new Directory("A-Directory-In-Final-Directory");
        sub3.addSubDirectory(insideSub3);

        root.printFiles();
        root.printSubDirectories();


    }

}
