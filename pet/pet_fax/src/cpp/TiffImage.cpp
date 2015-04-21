#include "StdAfx.h"
#pragma warning(disable:4251)
#include "TiffImage.h"

#include <vector>
#include "Magick++.h"
#ifdef _DEBUG
#pragma comment(lib,"CORE_DB_magick_.lib")
#pragma comment(lib,"CORE_DB_Magick++_.lib")
#pragma comment(lib,"CORE_DB_tiff_.lib")
#pragma comment(lib,"CORE_DB_wand_.lib")
#else
#pragma comment(lib,"CORE_RL_magick_.lib")
#pragma comment(lib,"CORE_RL_Magick++_.lib")
#pragma comment(lib,"CORE_RL_tiff_.lib")
#pragma comment(lib,"CORE_RL_wand_.lib")
#endif


TiffImage::TiffImage(const std::string& file)
	:m_filename(file)
{

}


TiffImage::~TiffImage(void)
{
}

std::string TiffImage::targetPath()
{
	return m_target_file;
}

void TiffImage::converVertical()
{
	using namespace Magick;
	const int const_width=WIDTH;
	const int const_height=HEIGHT;
	const int const_content_width=WIDTH-2*SPACE;
	const int const_content_height=HEIGHT-2*SPACE;

	Magick::Image image;
	image.read(m_filename);
	Magick::resizeImage sizeImg=Magick::resizeImage(Geometry(const_content_width,0));
	
	//Magick::rotateImage rotate(270);
	Magick::densityImage si=Magick::densityImage(Geometry(120,120));
	Magick::Geometry content_size(const_content_width,const_content_height);
	Magick::Geometry size(const_width,const_height);

	Magick::sampleImage sampleImg(content_size);
	Magick::resolutionUnitsImage rui = Magick::resolutionUnitsImage(PixelsPerCentimeterResolution);
	Magick::magickImage magickImg=Magick::magickImage("TIF");
	Magick::pageImage pageImg=Magick::pageImage(content_size);
	Magick::colorSpaceImage space(CMYKColorspace);
	Magick::rotateImage rotate_v(180);
	Magick::stripImage stripImg;
	Magick::qualityImage qualityImg(100);
	Magick::depthImage di(8);
	rui(image);
	sizeImg(image);
	
	Magick::Geometry geometry = image.size();

	size_t all_width=geometry.height();

	//rotate(image);
	//si(image);
	size_t startX=0,
		startY=0;
	std::vector<Magick::Image> imageList;
	while(true)
	{
		if(startY>=all_width)
		{
			break;
		}
		Magick::Geometry pageSize(const_content_width,const_content_height,startX,startY);
		Magick::Image cropImg(image.size(),Magick::Color("#ffffff"));	
		cropImg.composite(image,startX,0);
		Magick::cropImage cropImage(pageSize);
		cropImage(cropImg);
		magickImg(cropImg);
		pageImg(cropImg);
		space(cropImg);
		di(cropImg);
		stripImg(cropImg);
		qualityImg(cropImg);
		sampleImg(cropImg);
		rotate_v(cropImg);

		Magick::Geometry imageSize=cropImg.size();

		Magick::Image result(size,Magick::Color("#ffffff"));		
		magickImg(result);
		space(result);
		result.composite(cropImg,SPACE,size.height()-imageSize.height());
		di(result);
		stripImg(result);
		qualityImg(result);
		imageList.push_back(result);
		startY+=const_content_height;
		
	}
	m_target_file = m_filename.substr(0,m_filename.find_last_of('.')).append(".tif");
	if(imageList.size()>1){
		Magick::writeImages(imageList.begin(),imageList.end(),m_target_file);
	}else{
		imageList.at(0).write(m_target_file);
	}
}

void TiffImage::converHorizontal()
{
	using namespace Magick;
	const int const_width=WIDTH;
	const int const_height=HEIGHT;
	const int const_content_width=WIDTH-2*SPACE;
	const int const_content_height=HEIGHT-2*SPACE;

	Magick::Image image;
	image.read(m_filename);
	Magick::resizeImage sizeImg=Magick::resizeImage(Geometry(const_content_height,0));
	
	Magick::rotateImage rotate(270);
	Magick::densityImage si=Magick::densityImage(Geometry(120,120));
	Magick::Geometry content_size(const_content_width,const_content_height);
	Magick::Geometry size(const_width,const_height);

	Magick::sampleImage sampleImg(content_size);
	Magick::resolutionUnitsImage rui = Magick::resolutionUnitsImage(PixelsPerCentimeterResolution);
	Magick::magickImage magickImg=Magick::magickImage("TIF");
	Magick::pageImage pageImg=Magick::pageImage(content_size);
	Magick::colorSpaceImage space(CMYKColorspace);

	Magick::rotateImage rotate_v(180);

	Magick::stripImage stripImg;
	Magick::qualityImage qualityImg(100);
	Magick::depthImage di(8);
	rui(image);
	sizeImg(image);
	
	Magick::Geometry geometry = image.size();

	size_t all_width=geometry.height();

	rotate(image);
	//si(image);
	size_t startX=0,
		startY=0;
	std::vector<Magick::Image> imageList;
	while(true)
	{
		if(startX>=all_width)
		{
			break;
		}
		Magick::Geometry pageSize(const_content_width,const_content_height,startX,0);
		Magick::Image cropImg(image.size(),Magick::Color("#ffffff"));	
		cropImg.composite(image,0,0);
		Magick::cropImage cropImage(pageSize);
		cropImage(cropImg);
		magickImg(cropImg);
		pageImg(cropImg);
		space(cropImg);
		di(cropImg);
		stripImg(cropImg);
		qualityImg(cropImg);
		sampleImg(cropImg);
		rotate_v(cropImg);

		Magick::Geometry imageSize=cropImg.size();

		Magick::Image result(size,Magick::Color("#ffffff"));		
		magickImg(result);
		space(result);
		result.composite(cropImg,size.width()-imageSize.width(),SPACE);
		di(result);
		stripImg(result);
		qualityImg(result);
		imageList.push_back(result);
		startX+=const_content_width;
		
	}
	m_target_file = m_filename.substr(0,m_filename.find_last_of('.')).append(".tif");
	if(imageList.size()>1){
		Magick::writeImages(imageList.begin(),imageList.end(),m_target_file);
	}else{
		imageList.at(0).write(m_target_file);
	}
}
