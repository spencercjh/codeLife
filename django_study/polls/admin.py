from django.contrib import admin

from .models import *

# Register your models here.

admin.site.register(Choice)


class ChoiceInline(admin.TabularInline):
    model = Choice
    extra = 4


class QuestionAdmin(admin.ModelAdmin):
    fieldsets = [
        (
            'Question information',
            {
                'fields': ['question_text']
            }
        ),
        (
            'Date information',
            {
                'fields': ['pub_date'],
                'classes': ['collapse']
            }
        )
    ]
    inlines = [ChoiceInline]
    list_display = ('question_text',
                    'pub_date',
                    'was_published_recently')
    list_filter = ['pub_date']
    search_fields = ['question_text']


admin.site.register(Question, QuestionAdmin)
