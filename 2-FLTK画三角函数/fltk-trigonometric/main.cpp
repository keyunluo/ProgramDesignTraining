#include <FL/Fl.H>
#include <FL/fl_draw.H>
#include <FL/Fl_Choice.H>
#include <FL/Fl_Double_Window.H>
#include <cmath>

static Fl_Double_Window *w=0;
static int choiceIndex=0;


class DrawX : public Fl_Widget
{
public:
    DrawX(int X, int Y, int W, int H, const char*L=0) : Fl_Widget(X,Y,W,H,L)
    {
    }
    void draw()
    {
        fl_color(FL_BLUE);
        int x1 = x(),       y1 = y()+40;
        int x2 = x()+w()-1, y2 = y()+h()-1;
        int baseX=x1+(x2-x1)/2;
        int baseY=y1+(y2-y1)/2;
        fl_xyline(x1,y1+(y2-y1)/2,x2);
        fl_yxline(x1+(x2-x1)/2, y1,y2);
        fl_line(x2/2-10,y1+10,x2/2,y1);
        fl_line(x2/2+10,y1+10,x2/2,y1);
        fl_line(x2-10,y1+(y2-y1)/2-10,x2,y1+(y2-y1)/2);
        fl_line(x2-10,y1+(y2-y1)/2+10,x2,y1+(y2-y1)/2);
        fl_draw("0",x1+(x2-x1)/2-10,y1+(y2-y1)/2+15);

        for(int i=baseX-240;i<baseX+250;i=i+10)
        {
            fl_yxline(i,baseY,baseY-5);
        }
        fl_draw("X",x2-20,baseY+20);
        for(int j=baseY-180;j<baseY+180;j=j+10)
        {
            fl_xyline(baseX,j,baseX+5);
        }
       fl_draw("Y",baseX-20,y1+20);
        switch (choiceIndex) {
        case 0:
            fl_color(FL_RED);
            for (double i=-240+baseX;i<240+baseX;i=i+0.1)
            {
                float xPoint=(i-baseX)/-240.0*3.1415926;
                float yPoint=80*sin(xPoint)+baseY;
                fl_point(i,yPoint);
            }
            break;
        case 1:
            fl_color(FL_YELLOW);
            for (double i=-240+baseX;i<240+baseX;i=i+0.1)
            {
                float xPoint=(i-baseX)/240.0*3.1415926;
                float yPoint=-80*cos(xPoint)+baseY;
                fl_point(i,yPoint);
            }
            break;
        case 2:
            fl_color(FL_BLACK);
            for (double i=-240+baseX;i<240+baseX;i=i+0.1)
            {
                float xPoint=(i-baseX)/240.0*3.1415926;
                float yPoint=80*tan(xPoint)+baseY;
                fl_point(i,yPoint);
            }
            break;
        case 3:
            fl_color(FL_WHITE);
            for (double i=-240+baseX;i<240+baseX;i=i+0.1)
            {
                float xPoint=(i-baseX)/-240.0*3.1415926;
                float yPoint=2*80*sin(2*xPoint-0.2)+baseY;
                fl_point(i,yPoint);
            }
            break;
        default:
            fl_color(FL_GREEN);
            fl_line(x1,y1,x2/2,y2);
            break;
        }
    }
};


 static void Choice_CB(Fl_Widget*widget)
 {
     Fl_Choice *ch = (Fl_Choice*)widget;
     choiceIndex=ch->value();
     DrawX *draw_x=new DrawX(0, 0, w->w(), w->h(),0);
     w->resizable(draw_x);
     w->redraw();
 }


int main()
{
    w=new Fl_Double_Window(500,450,"Draw ");
    Fl_Choice *choice = new Fl_Choice(60,10,140,25,"选择:");
    choice->add("y=sin(x)");
    choice->add("y=cos(x)");
    choice->add("y=tan(x)");
    choice->add("y=2*six(2x-0.1)");

    choice->callback((Fl_Callback*)Choice_CB);
    choice->take_focus();
    choice->value(0);
    choice->do_callback();

    w->end();
    w->show();
    return(Fl::run());
}
