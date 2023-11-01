package edu.project1;


public interface GallowsProvider {
    String getGallows();

    int getLeftAttempts();

    void buildNextElement();

    void destroyGallows();

}
