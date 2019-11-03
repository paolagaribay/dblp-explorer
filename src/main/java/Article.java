import java.util.List;

public class Article {
    String title;
    String id;
    int citations;
    List<String> references;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public int getCitations() {
        return citations;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public List<String> getReferences() {
        return references;
    }

    public void printArticle() {
        System.out.println(title);
    }
}
