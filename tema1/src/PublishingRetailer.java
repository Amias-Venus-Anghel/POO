public class PublishingRetailer {
    private int ID;
    private String name;
    private IPublishingArtifact[] publishingArtifacts = new IPublishingArtifact[0];
    private Countries[] countries = new Countries[0];

    public PublishingRetailer(String id, String n){
        this.ID = Integer.parseInt(id);
        this.name = n;
    }

    public PublishingRetailer(){}

    public int getID() {
        return ID;
    }

    public IPublishingArtifact[] getPublishingArtifacts() {
        return publishingArtifacts;
    }

    public void addCountries(Countries c){
       Countries[] aux = new Countries[this.countries.length + 1];
        System.arraycopy(this.countries, 0, aux, 0, this.countries.length);
        this.countries = aux;
        this.countries[this.countries.length - 1] = c;
    }

    public Countries[] getCountries() {
        return countries;
    }

    public void addPubArtifact(IPublishingArtifact artifact){
        IPublishingArtifact[] aux = new IPublishingArtifact[this.publishingArtifacts.length + 1];
        System.arraycopy(this.publishingArtifacts, 0, aux, 0, this.publishingArtifacts.length);
        this.publishingArtifacts = aux;
        this.publishingArtifacts[this.publishingArtifacts.length - 1] = artifact;
    }
}
