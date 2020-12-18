from django.urls import path
from api import views

urlpatterns = [
    path('recognite/', views.recognite),
    path('delete/', views.delete),
    path('upload/', views.upload),
    path('all_users/', views.allUsers)
]