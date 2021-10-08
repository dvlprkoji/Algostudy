package com.example.algostudy.repository.Hashtag;

import com.example.algostudy.domain.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    
    public Hashtag findByName(String name);

    public Hashtag save(Hashtag hashtag);
    
    public default Hashtag findByNameOrSave(String name) {
        Hashtag hashtag = findByName(name);
        return hashtag!=null ? hashtag : save(Hashtag.builder().name(name).build());
    }
}


