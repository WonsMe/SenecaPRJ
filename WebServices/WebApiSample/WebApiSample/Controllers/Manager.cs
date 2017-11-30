using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApiSample.Models;

namespace WebApiSample.Controllers
{
    public class Manager
    {
        // AutoMapper
        MapperConfiguration config;
        public IMapper mapper;

        // Data context
        private ApplicationDbContext ds = new ApplicationDbContext();

        public Manager()
        {
            // Configure the AutoMapper components
            config = new MapperConfiguration(cfg =>
            {
                cfg.CreateMap<Models.Title, Controllers.TitleBase>();
                cfg.CreateMap<Controllers.TitleAdd, Models.Title>();
            });

            mapper = config.CreateMapper();
        }

        public IEnumerable<TitleBase> TitleGetAll()
        {
            var c = ds.Titles.OrderBy(t => t.Id);
            return mapper.Map<IEnumerable<TitleBase>>(c);
        }

        public TitleBase TitleGetById(int id)
        {
            var o = ds.Titles.Find(id);
            return (o == null) ? null : mapper.Map<TitleBase>(o);
        }

        public TitleBase TitleAdd(TitleAdd newItem)
        {
            // Ensure that we can continue
            if (newItem == null)
            {
                return null;
            }
            else
            {
                // Add the new object
                var addedItem = mapper.Map<Title>(newItem);

                ds.Titles.Add(addedItem);
                ds.SaveChanges();

                // Transform and return
                return mapper.Map<TitleBase>(addedItem);
            }
        }
    }
}