package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*

object WelcomePage extends PetclinicPage:

  override def pageSettings = super.pageSettings
    .withTitle("Welcome!")

  override def pageContent: Frag = div(
    h1("Welcome!"),
    s"""
      This is a [PetClinic](https://spring-petclinic.github.io/) implementation  
      with the [Sharaf](https://github.com/sake92/sharaf) mini framework.

      [GitHub source](https://github.com/sake92/sharaf-petclinic)
    """.md
  )
