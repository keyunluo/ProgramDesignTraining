
// MFCDrawView.h : CMFCDrawView 类的接口
//

#pragma once


class CMFCDrawView : public CView
{
protected: // 仅从序列化创建
	CMFCDrawView();
	DECLARE_DYNCREATE(CMFCDrawView)

// 特性
public:
	CMFCDrawDoc* GetDocument() const;

// 操作
public:

// 重写
public:
	virtual void OnDraw(CDC* pDC);  // 重写以绘制该视图
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// 实现
public:
	virtual ~CMFCDrawView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// 生成的消息映射函数
protected:
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnDrawLine();
	afx_msg void OnDrawPencil();
	afx_msg void OnDrawRect();
	afx_msg void OnDrawEllipse();
	afx_msg void OnDrawFont();
	afx_msg void OnDrawRundrect();


// 属性
public:

	int       DrawingType;

	COLORREF  penColor;//颜色可能因为是整型，所以可以直接序列化
	int       penStyle;
	int       penWide;
	int       brushStyle;
	COLORREF  brushColor;

	int       pictureFourStyle;
	CPoint    startPoint;
	CPoint    newEndPoint;
	CPoint    oldEndPoint;

	LOGFONT   logFont;
	CFont*    pFont;
	COLORREF  fontColor;
	CString   stringFont;

	BOOL      clickAndMove;//标志按下鼠标左键按钮时是否画过图

	afx_msg void OnFont();
	afx_msg void OnDrawColor();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnChangeLineWide(UINT uID);
	afx_msg void OnLinePenStyle(UINT uID);
	afx_msg void OnPictureStyle(UINT uID);
	afx_msg void OnLineFillStyle(UINT uID);

};

#ifndef _DEBUG  // MFCDrawView.cpp 中的调试版本
inline CMFCDrawDoc* CMFCDrawView::GetDocument() const
   { return reinterpret_cast<CMFCDrawDoc*>(m_pDocument); }
#endif

