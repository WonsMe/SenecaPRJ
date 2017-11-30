using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace WebApiSample.Controllers
{
    public class TitlesController : ApiController
    {
        private Manager m = new Manager();

        // GET: api/Titles
        public IHttpActionResult Get()
        {
            return Ok(m.TitleGetAll());
        }

        // GET: api/Titles/5
        public IHttpActionResult Get(int? id)
        {
            var o = m.TitleGetById(id.GetValueOrDefault());

            if (o == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(o);
            }
        }

        // POST: api/Titles
        public IHttpActionResult Post([FromBody]TitleAdd newItem)
        {
            // Ensure that the URI is clean (and does not have an id parameter)
            if (Request.GetRouteData().Values["id"] != null)
            {
                return BadRequest("Invalid request URI");
            }

            // Ensure that a "newItem" is in the entity body
            if (newItem == null)
            {
                return BadRequest("Must send an entity body with the request");
            }

            // Ensure that we can use the incoming data
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            // Attempt to add the new item...
            var addedItem = m.TitleAdd(newItem);

            // Continue?
            if (addedItem == null)
            {
                return BadRequest("Cannot add the object");
            }

            // Return HTTP 201 with the new object in the message (entity) body
            // Notice how to create the URI for the required "Location" header
            var uri = Url.Link("DefaultApi", new { id = addedItem.Id });

            return Created(uri, addedItem);
        }

        //// PUT: api/Titles/5
        //public void Put(int id, [FromBody]string value)
        //{
        //}

        //// DELETE: api/Titles/5
        //public void Delete(int id)
        //{
        //}
    }
}
