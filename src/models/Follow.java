package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
        @NamedQuery(name = "getAllFollows", query = "SELECT f FROM Follow AS f"),//フォロー関係のデータ全て
        @NamedQuery(name = "getFollowsCount", query = "SELECT COUNT(f) FROM Follow AS f"),//データ全件の件数をカウント
        @NamedQuery(name = "getFollowerCount",query= "SELECT COUNT(f) FROM Follow As f WHERE f.follower = :me"),//ログインしている従業員がフォローしている件数をカウント
        @NamedQuery(name ="getFolloweeCount",query="SELECT COUNT(f) FROM Follow As f WHERE f.followee = :me"),//ログインしている従業員がフォローされている件数をカウント？
        @NamedQuery(name ="getFollow",query="SELECT f FROM Follow AS f WHERE f.follower=:follower AND f.followee=:followee")//この画面で表示されている従業員をフォローしているかしていないかの判定
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower",nullable=false)
    private Employee follower;

    @ManyToOne
    @JoinColumn(name="followee",nullable=false)
    private Employee followee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower) {
        this.follower = follower;
    }

    public Employee getFollowee() {
        return followee;
    }

    public void setFollowee(Employee followee) {
        this.followee = followee;
    }


}
