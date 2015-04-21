#pragma once
#include <string>

class TiffImage
{
	enum{WIDTH=794,HEIGHT=1123,SPACE=20};
public:
	TiffImage(const std::string& filename);
	~TiffImage(void);
	//转换竖着的图片
	void converVertical();
	//横着的图片(门票使用)
	void converHorizontal();

	std::string targetPath();



private:
	std::string m_filename;
	long m_type;
	std::string m_target_file;


	
};

