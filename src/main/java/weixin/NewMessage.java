package weixin;

import java.util.List;

//��װͼ����Ϣxml
public class NewMessage extends BaseMessage{
	private int ArticleCount;//ͼ����Ϣ����������Ϊ8������
	private List<News> Articles;//����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ,ע�⣬���ͼ��������8���򽫻�����Ӧ
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
	
}
