from django.urls import path
from api import views

urlpatterns = [
    path('recognite/', views.recognite),
    path('request/', views.request),
]