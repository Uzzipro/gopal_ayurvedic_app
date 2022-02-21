package com.app.gopalayurvediccenter.Dataclass;

import com.app.gopalayurvediccenter.Adapters.SocialMediaAdapter;

public class SocialMediaDto {
    private String socialMediaName;
    private int socialMediaLogo;
    private String mediaLinks;


    public String getMediaLinks() {
        return mediaLinks;
    }

    public void setMediaLinks(String mediaLinks) {
        this.mediaLinks = mediaLinks;
    }

    public int getSocialMediaLogo() {
        return socialMediaLogo;
    }

    public void setSocialMediaLogo(int socialMediaLogo) {
        this.socialMediaLogo = socialMediaLogo;
    }

    public String getSocialMediaName() {
        return socialMediaName;
    }

    public void setSocialMediaName(String socialMediaName) {
        this.socialMediaName = socialMediaName;
    }


    public SocialMediaDto(String socialMediaName, int socialMediaLogo, String mediaLinks)
    {
        this.socialMediaName = socialMediaName;
        this.socialMediaLogo = socialMediaLogo;
        this.mediaLinks = mediaLinks;
    }

    public SocialMediaDto()
    {

    }
}
