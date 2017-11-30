using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WebApiSample.Controllers
{
    public class TitleAdd
    {
        [Required, StringLength(100)]
        public string Name { get; set; }
    }

    public class TitleBase : TitleAdd
    {
        public int Id { get; set; }
    }
}