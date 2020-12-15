from django.urls import path
from api import views

urlpatterns = [
    path('recognite/', views.recognite),
    path('delete/<slug:slug>/', views.request, name='unique_slug'),
    path('upload/', views.upload),
]