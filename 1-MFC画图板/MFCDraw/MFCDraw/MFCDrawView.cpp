
// MFCDrawView.cpp : CMFCDrawView 类的实现
//

#include "stdafx.h"
// SHARED_HANDLERS 可以在实现预览、缩略图和搜索筛选器句柄的
// ATL 项目中进行定义，并允许与该项目共享文档代码。
#ifndef SHARED_HANDLERS
#include "MFCDraw.h"
#endif

#include "MFCDrawDoc.h"
#include "MFCDrawView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

#include "InputDlg.h"
// CMFCDrawView

IMPLEMENT_DYNCREATE(CMFCDrawView, CView)

BEGIN_MESSAGE_MAP(CMFCDrawView, CView)
	// 标准打印命令
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
	ON_COMMAND(ID_DRAW_LINE, &CMFCDrawView::OnDrawLine)
	ON_COMMAND(ID_DRAW_PENCIL, &CMFCDrawView::OnDrawPencil)
	ON_COMMAND(ID_DRAW_RECT, &CMFCDrawView::OnDrawRect)
	ON_COMMAND(ID_DRAW_ELLIPSE, &CMFCDrawView::OnDrawEllipse)
	ON_COMMAND(ID_DRAW_FONT, &CMFCDrawView::OnDrawFont)
	ON_COMMAND(ID_DRAW_RUNDRECT, &CMFCDrawView::OnDrawRundrect)
	ON_COMMAND(ID_LOGFONT, &CMFCDrawView::OnFont)
	ON_COMMAND(ID_DRAW_COLOR, &CMFCDrawView::OnDrawColor)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()

	//线条宽度
	ON_COMMAND_RANGE(ID_LINE_MIN, ID_LINE_ADDWIDE, OnChangeLineWide)

	//线条样式映射
	ON_COMMAND_RANGE(ID_PS_SOLID, ID_PS_INSIDEFRAME, OnLinePenStyle)
	//图形样式映射
	ON_COMMAND_RANGE(ID_ONLY_FRAME, ID_ONLY_FILL, OnPictureStyle)
	//六种线条填充样式映射
	ON_COMMAND_RANGE(ID_HS_CROSS, ID_HS_VERTICAL, OnLineFillStyle)

END_MESSAGE_MAP()

// CMFCDrawView 构造/析构

CMFCDrawView::CMFCDrawView()
{
	clickAndMove = FALSE;
	DrawingType = 1;//初始为直线
	penStyle = PS_SOLID;//实线
	penWide = 1;//宽度为一个像素
	penColor = RGB(128, 128, 128);//默认为灰色  
	brushStyle = HS_CROSS;//初始为网格
	brushColor = RGB(255, 255, 255);//初始化为白色
	pictureFourStyle = 1;//初始为只有边框,无填充
	pFont = NULL;
}

CMFCDrawView::~CMFCDrawView()
{
}

BOOL CMFCDrawView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: 在此处通过修改
	//  CREATESTRUCT cs 来修改窗口类或样式

	return CView::PreCreateWindow(cs);
}

// CMFCDrawView 绘制

void CMFCDrawView::OnDraw(CDC*  pDC)
{
	CMFCDrawDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	POSITION pos = pDoc->shapeArray.GetHeadPosition();
	while (pos != NULL)
	{
		Shapes *shp = pDoc->shapeArray.GetNext(pos);
		if (shp->m_DrawingType == 1)
		{
			CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
			pDC->SelectObject(&pen);
			pDC->MoveTo(shp->m_startPoint);
			pDC->LineTo(shp->m_endPoint);
		}
		else if (shp->m_DrawingType == 2)
		{
			if (shp->m_pictureFourStyle == 1)//有边框，无填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				pDC->SelectObject(&pen);
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 2)//有边框，白色填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 3)//无边框，颜色填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 4)//有边框，线条填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushStyle, shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}

		}
		else if (shp->m_DrawingType == 3)
		{
			if (shp->m_pictureFourStyle == 1)//有边框，无填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				pDC->SelectObject(&pen);
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
			else if (shp->m_pictureFourStyle == 2)//有边框，白填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
			else if (shp->m_pictureFourStyle == 3)//无边框，颜色填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
			else if (shp->m_pictureFourStyle == 4)//有边框，线条填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushStyle, shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
		}
		else if (shp->m_DrawingType == 4)//画椭圆
		{
			if (shp->m_pictureFourStyle == 1)//有边框，无填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				pDC->SelectObject(&pen);
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 2)//有边框，白填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 3)//无边框，颜色填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 4)//有边框，线条填充
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushStyle, shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
		}
		else if (shp->m_DrawingType == 5)//画字符
		{
			pFont = new CFont();
			pFont->CreateFontIndirect(&shp->m_logFont);
			pDC->SetBkMode(TRANSPARENT);
			pDC->SetTextColor(shp->m_fontColor);
			pDC->SelectObject(pFont);
			pDC->TextOut(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_Text);
		}

		else if (shp->m_DrawingType == 6)//画铅笔字
		{
			CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
			pDC->SelectObject(&pen);
			pDC->MoveTo(shp->m_startPoint);
			pDC->LineTo(shp->m_endPoint);
		}
	}
}


// CMFCDrawView 打印

BOOL CMFCDrawView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// 默认准备
	return DoPreparePrinting(pInfo);
}

void CMFCDrawView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: 添加额外的打印前进行的初始化过程
}

void CMFCDrawView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: 添加打印后进行的清理过程
}


// CMFCDrawView 诊断

#ifdef _DEBUG
void CMFCDrawView::AssertValid() const
{
	CView::AssertValid();
}

void CMFCDrawView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CMFCDrawDoc* CMFCDrawView::GetDocument() const // 非调试版本是内联的
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CMFCDrawDoc)));
	return (CMFCDrawDoc*)m_pDocument;
}
#endif //_DEBUG


// CMFCDrawView 消息处理程序


void CMFCDrawView::OnDrawLine()
{
	DrawingType = 1;
}


void CMFCDrawView::OnDrawRect()
{
	DrawingType = 2;
}

void CMFCDrawView::OnDrawRundrect()
{
	DrawingType = 3;
}

void CMFCDrawView::OnDrawEllipse()
{
	DrawingType = 4;
}


void CMFCDrawView::OnDrawFont()
{
	if (!pFont)
	{
		MessageBox(L"请先设置字体！");
		return;
	}
	DrawingType = 5;
}

void CMFCDrawView::OnDrawPencil()
{
	DrawingType = 6;
}


void CMFCDrawView::OnFont()
{
	CFontDialog fontDialog(&logFont);
	int result = fontDialog.DoModal();

	if (result == IDOK)
	{
		pFont = new CFont();
		pFont->CreateFontIndirect(&logFont);
		fontColor = fontDialog.GetColor();
		MessageBox(L"字体设置成功");
		return;
	}
	MessageBox(L"取消字体设置");
}


void CMFCDrawView::OnDrawColor()
{
	CColorDialog colorSet(0, 0, NULL);
	int result = colorSet.DoModal();
	if (result == IDOK)
	{
		brushColor = penColor = colorSet.GetColor();
	}
}

void CMFCDrawView::OnChangeLineWide(UINT uID)
{
	switch (uID)
	{
	case ID_LINE_MIN://细
		penWide = 1;
		break;
	case ID_LINE_MID://中
		penWide = 5;
		break;
	case ID_LINE_WIDE://宽
		penWide = 18;
		break;
	case ID_LINE_ADDWIDE://加宽
		penWide++;
		if (penWide > 30)//设置最宽为30个像素
		{
			penWide = 1;
		}
		break;
	}
}


//线条样式设置
void CMFCDrawView::OnLinePenStyle(UINT uID)
{
	switch (uID)
	{
	case ID_PS_SOLID:
		penStyle = PS_SOLID;
		break;
	case ID_PS_DASH:
		penStyle = PS_DASH;
		MessageBox(L"选择了PS_DASH");
		break;
	case ID_PS_DOT:
		penStyle = PS_DOT;
		break;
	case ID_PS_DASHDOT:
		penStyle = PS_DASHDOT;
		MessageBox(L"选择了PS_DASHDOT");
		break;
	case ID_PS_DASHDOTDOT:
		penStyle = PS_DASHDOTDOT;
		break;
	case ID_PS_NULL:
		penStyle = PS_NULL;
		break;
	case ID_PS_INSIDEFRAME:
		penStyle = PS_INSIDEFRAME;
		break;
	}
}
//图形样式设置
void CMFCDrawView::OnPictureStyle(UINT uID)
{
	switch (uID)
	{
	case ID_ONLY_FRAME:
		pictureFourStyle = 1;//只有边框
		break;
	case ID_FRAME_FILL:
		pictureFourStyle = 2;//边框和填充
		break;
	case ID_ONLY_FILL:
		pictureFourStyle = 3;//只填充
		penStyle = PS_SOLID;
		brushColor = penColor;
		break;
	}
}
//六种填充样式设置
void CMFCDrawView::OnLineFillStyle(UINT uID)
{
	pictureFourStyle = 4;
	switch (uID)
	{
	case ID_HS_BDIAGONAL:
		brushStyle = HS_BDIAGONAL;
		break;
	case ID_HS_CROSS:
		brushStyle = HS_CROSS;
		break;
	case ID_HS_DIAGCROSS:
		brushStyle = HS_DIAGCROSS;
		break;
	case ID_HS_FDIAGONAL:
		brushStyle = HS_FDIAGONAL;
		break;
	case ID_HS_HORIZONTAL:
		brushStyle = HS_HORIZONTAL;
		break;
	case ID_HS_VERTICAL:
		brushStyle = HS_VERTICAL;
		break;
	}
}

void CMFCDrawView::OnLButtonDown(UINT nFlags, CPoint point)
{
	CMFCDrawDoc* pDoc = GetDocument();
	SetCapture();
	startPoint = oldEndPoint = newEndPoint = point;
	if (DrawingType == 5)//写字
	{
		InputDlg dlg;
		int result = dlg.DoModal();
		if (result == IDOK)
		{
			Shapes *sh = new Shapes();
			CString s = dlg.m_Edit;
			CDC*pDC = GetDC();
			//设置文字背景透明
			pDC->SetBkMode(TRANSPARENT);	 
			pDC->SelectObject(pFont);
			pDC->SetTextColor(fontColor);
			pDC->TextOut(point.x, point.y, s);
			if (!s.IsEmpty())
			{
				sh->m_DrawingType = DrawingType;
				sh->m_startPoint = point;
				sh->m_logFont = logFont;
				sh->m_fontColor = fontColor;
				sh->m_Text = s;
				pDoc->shapeArray.AddTail(sh);
				pDoc->SetModifiedFlag();
			}
		}
	}
	CView::OnLButtonDown(nFlags, point);
}


void CMFCDrawView::OnLButtonUp(UINT nFlags, CPoint point)
{
	ReleaseCapture();
	CDC *pDC = GetDC();
	CMFCDrawDoc *pDoc = GetDocument();
	if (clickAndMove)
	{
		Shapes *shap = new Shapes();
		CPen pen(penStyle, penWide, penColor);
		pDC->SelectObject(&pen);

		if (DrawingType == 1)//直线
		{
			pDC->MoveTo(startPoint);
			pDC->LineTo(newEndPoint);
		}
		else if (DrawingType == 2)//矩形
		{
			if (pictureFourStyle == 1)//有边框，无填充
			{
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//有边框，白填充
			{
				brushColor = RGB(255, 255, 255);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//有边框，颜色填充
			{

				pDC->SelectObject(&pen);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//有边框，线条填充
			{
				brushColor = penColor;
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 3)//圆角矩形
		{
			if (pictureFourStyle == 1)//有边框，无填充
			{
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 2)//有边框，白填充
			{
				brushColor = RGB(255, 255, 255);
				shap->m_brushColor = RGB(255, 255, 255);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 3)//有边框，颜色填充
			{
				brushColor = penColor;
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 4)//有边框，线条填充
			{
				brushColor = penColor;
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
		}
		else if (DrawingType == 4)//椭圆
		{
			if (pictureFourStyle == 1)//有边框，无填充
			{
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//有边框，白色填充
			{
				brushColor = RGB(255, 255, 255);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//有边框，颜色填充
			{
				brushColor = penColor;
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);

				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//有边框，线条填充
			{
				brushColor = penColor;
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 6)
		{
			CView::OnLButtonUp(nFlags, point);
			return;
		}

		shap->m_DrawingType = DrawingType;
		shap->m_startPoint = startPoint;
		shap->m_endPoint = newEndPoint;
		shap->m_penStyle = penStyle;
		shap->m_penWide = penWide;
		shap->m_penColor = penColor;
		//如果保存直线，只要保存上面信息已经足够
		shap->m_pictureFourStyle = pictureFourStyle;
		shap->m_brushStyle = brushStyle;
		shap->m_brushColor = brushColor;
		clickAndMove = FALSE;
		pDoc->shapeArray.AddTail(shap);
		pDoc->SetModifiedFlag();
	}
	CView::OnLButtonUp(nFlags, point);
}


void CMFCDrawView::OnMouseMove(UINT nFlags, CPoint point)
{
	newEndPoint = point;
	CDC*pDC = GetDC();
	if (nFlags&MK_LBUTTON)
	{
		clickAndMove = TRUE;
		if (DrawingType == 1)//直线
		{
			CPen pen(penStyle, penWide, penColor);
			pDC->SelectObject(&pen);

			pDC->SetROP2(R2_XORPEN);

			pDC->MoveTo(startPoint);
			pDC->LineTo(oldEndPoint);

			pDC->MoveTo(startPoint);
			pDC->LineTo(newEndPoint);
		}
		else if (DrawingType == 2)//直角矩形
		{
			if (pictureFourStyle == 1)//有边框，无填充
			{
				// MessageBox("直角矩形1");
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->SelectStockObject(NULL_BRUSH);

				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//有边框，白色填充
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//有边框，颜色填充
			{
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//有边框，线条填充
			{
				brushColor = penColor;
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 3)//圆角矩形
		{
			if (pictureFourStyle == 1)//有边框，无填充
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->SelectStockObject(NULL_BRUSH);

				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
				//MessageBox("圆角矩形1");
			}
			else if (pictureFourStyle == 2)//有边框，白填充
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 3)//有边框，颜色填充
			{
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 4)//有边框，线条填充
			{
				brushColor = penColor;
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
		}
		else if (DrawingType == 4)//椭圆
		{
			if (pictureFourStyle == 1)//有边框，无填充
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->SelectStockObject(NULL_BRUSH);

				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//有边框，白填充
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//有边框，颜色填充
			{
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//有边框，线条填充
			{
				brushColor = penColor;
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 6)//实现铅笔功能
		{
			CPen pen(penStyle, penWide, penColor);
			pDC->SelectObject(&pen);
			pDC->MoveTo(oldEndPoint);
			pDC->LineTo(newEndPoint);

			CMFCDrawDoc *pDoc = GetDocument();
			Shapes *shap = new Shapes();
			shap->m_DrawingType = DrawingType;
		    shap->m_startPoint = oldEndPoint;
			shap->m_endPoint = newEndPoint;		
			shap->m_penStyle = penStyle;
			shap->m_penWide = penWide;
			shap->m_penColor = penColor;
			pDoc->shapeArray.AddTail(shap);
			pDoc->SetModifiedFlag();
		}
		oldEndPoint = newEndPoint;
	}

	CView::OnMouseMove(nFlags, point);
}
